package com.beyondsoft.rdc.cloud.iot.iam.server.images.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelBo;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelVo;
import com.beyondsoft.rdc.cloud.iot.iam.client.websocket.server.WebSocketServer;
import com.beyondsoft.rdc.cloud.iot.iam.common.constant.IAMConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.constant.SessionConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.LoginException;
import com.beyondsoft.rdc.cloud.iot.iam.common.fastdfs.FastDfsClientWrapper;
import com.beyondsoft.rdc.cloud.iot.iam.common.filter.MySessionContext;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.util.CollectionCopyUtil;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.mapper.ImagesMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.*;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesLabelService;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesService;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.util.ImagesUtil;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.service.PushDataService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private ImagesLabelService imagesLabelService;

    @Autowired
    private ImagesDeviceService imagesDeviceService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private PushDataService pushDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer merchantId, Integer id) {
        List<ImagesDeviceVo> imagesDeviceVoList = this.imagesDeviceService.getImagesByDevice(id);
        List<IamDeviceDo> iamDeviceDoList = JSON.parseArray(JSON.toJSONString(imagesDeviceVoList), IamDeviceDo.class);

        //执行删除
        this.imagesLabelService.deleteImagesId(id);
        //执行删除
        this.imagesDeviceService.deleteImagesId(id);

        ImagesVo imagesVo = this.imagesMapper.selectByPrimaryKey(id);
        this.fastDfsClientWrapper.deleteFile(imagesVo.getImagesPath());

        int num = this.imagesMapper.deleteByPrimaryKey(id);

        //websockert给设备推消息
        WebSocketServer.sendImagesUpdate(merchantId, iamDeviceDoList);
        return num;
    }

    @Override
    public int insert(ImagesBo record) {
        ImagesDo imagesDo = new ImagesDo();
        BeanUtils.copyProperties(record, imagesDo);
        return this.imagesMapper.insert(imagesDo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(ImagesBo record) {
        ImagesDo imagesDo = new ImagesDo();
        BeanUtils.copyProperties(record, imagesDo);

        //判断内容标题唯一
        List<ImagesVo> imagesVoList = this.imagesMapper.getImagesBymerchantId(record.getMerchantId());
        String imagesName = record.getImagesName();
        for (ImagesVo imagesVo:imagesVoList) {
            if(imagesName.equals(imagesVo.getImagesName())){
                throw new GeneralException(InternationEnum.IMAGES_NAME_HAS_EXISTS.getLanguage(GlobalValue.getLanguage()));
            }
        }

        if(record.getImagesStartTime() != null && record.getImagesEndTime() != null){
            imagesDo.setImagesStartTime(new Date(record.getImagesStartTime()));
            imagesDo.setImagesEndTime(new Date(record.getImagesEndTime()));
        }

        //上传图片
        String imagePath = "";
        MultipartFile images = record.getImages();
        if (Objects.nonNull(images)){
            imagePath = fastDfsClientWrapper.uploadFile(images);
        }
        imagesDo.setImagesPath(fastDfsClientWrapper.pathHandle(imagePath));
        int num = this.imagesMapper.insertSelective(imagesDo);

        Integer imagesId = imagesDo.getId();
        //添加标签关系
        List<LabelBo> labelList = record.getLabelList();
        List<LabelDo> labelDoList = CollectionCopyUtil.copyList(labelList, LabelDo.class);
        this.imagesLabelService.batchInsert(labelDoList, imagesId);

        //添加设备关系
        List<IamDeviceBo> iamDeviceDoList = record.getDeviceList();
        List<IamDeviceDo> deviceDoList = CollectionCopyUtil.copyList(iamDeviceDoList, IamDeviceDo.class);
        this.imagesDeviceService.batchInsert(deviceDoList, imagesId);

        //websockert给设备推消息
        WebSocketServer.sendImagesUpdate(record.getMerchantId(), deviceDoList);
        return num;
    }

    @Override
    public ImagesVo selectByPrimaryKey(Integer id) {
        ImagesVo imagesVo = this.imagesMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(imagesVo)){
            imagesVo.setImagesPath(this.fastDfsClientWrapper.fileHttpFullPath(imagesVo.getImagesPath()));
        }
        Integer imagesId = imagesVo.getId();
        //获取图片标签
        List<ImagesLabelVo> imagesLabelVoList = this.imagesLabelService.getImagesByLabel(imagesId);
        imagesVo.setLabelVoList(imagesLabelVoList);
        //图片设备
        List<ImagesDeviceVo> deviceVoList = this.imagesDeviceService.getImagesByDevice(imagesId);
        imagesVo.setDeviceVoList(deviceVoList);
        return imagesVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ImagesBo record) {
        ImagesDo imagesDo = new ImagesDo();
        BeanUtils.copyProperties(record, imagesDo);
        if(record.getImagesStartTime() != null && record.getImagesEndTime() != null){
            imagesDo.setImagesStartTime(new Date(record.getImagesStartTime()));
            imagesDo.setImagesEndTime(new Date(record.getImagesEndTime()));
        }

        //判断内容标题唯一
        List<ImagesVo> imagesVoList = this.imagesMapper.getImagesBymerchantId(record.getMerchantId());
        String imagesName = record.getImagesName();
        Integer imagesId = record.getId();
        for (ImagesVo imagesVo:imagesVoList) {
            if(imagesName.equals(imagesVo.getImagesName()) && !imagesId.equals(imagesVo.getId())){
                throw new GeneralException(InternationEnum.IMAGES_NAME_HAS_EXISTS.getLanguage(GlobalValue.getLanguage()));
            }
        }

        //上传图片
        String imagePath = "";
        MultipartFile images = record.getImages();
        if (Objects.nonNull(images)){
            ImagesVo imagesVo = this.imagesMapper.selectByPrimaryKey(record.getId());
            if(!ObjectUtils.isEmpty(imagesVo)){
                fastDfsClientWrapper.deleteFile(imagesVo.getImagesPath());
            }
            imagePath = fastDfsClientWrapper.uploadFile(images);
            imagesDo.setImagesPath(fastDfsClientWrapper.pathHandle(imagePath));
        }

        //执行删除
        this.imagesLabelService.deleteImagesId(imagesId);
        //添加标签关系
        List<LabelBo> labelList = record.getLabelList();
        List<LabelDo> labelDoList = CollectionCopyUtil.copyList(labelList, LabelDo.class);
        this.imagesLabelService.batchInsert(labelDoList, imagesId);

        //执行删除
        this.imagesDeviceService.deleteImagesId(imagesId);
        //添加设备关系
        List<IamDeviceBo> iamDeviceDoList = record.getDeviceList();
        List<IamDeviceDo> deviceDoList = CollectionCopyUtil.copyList(iamDeviceDoList, IamDeviceDo.class);
        this.imagesDeviceService.batchInsert(deviceDoList, imagesId);

        int num = this.imagesMapper.updateByPrimaryKeySelective(imagesDo);

        //websockert给设备推消息
        WebSocketServer.sendImagesUpdate(record.getMerchantId(), deviceDoList);
        return num;
    }

    @Override
    public int updateByPrimaryKey(ImagesBo record) {
        ImagesDo imagesDo = new ImagesDo();
        BeanUtils.copyProperties(record, imagesDo);
        return this.imagesMapper.updateByPrimaryKey(imagesDo);
    }

    @Override
    public List<ImagesVo> getDeviceByImages(Integer merchantId, String deviceCode, String imagesName, String type) {
        List<ImagesVo> deviceByImages = this.imagesMapper.getDeviceByImages(merchantId, deviceCode, imagesName);
        deviceByImages.forEach(imagesVo -> {
            imagesVo.setImagesPath(this.fastDfsClientWrapper.fileHttpFullPath(imagesVo.getImagesPath()));
        });

        List<ImagesVo> resultList = deviceByImages;
        if(!"1".equals(type)){
            //是否在有效期内
            resultList = ImagesUtil.getImagesList(deviceByImages);
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteImages(Integer merchantId, List<Integer> imagesIdList) {
        if (imagesIdList.size() <= 0) {
            return 0;
        }
        List<ImagesDeviceVo> imagesDeviceVoList = this.imagesDeviceService.getImagesByDeviceBatch(imagesIdList);
        List<IamDeviceDo> iamDeviceDoList = JSON.parseArray(JSON.toJSONString(imagesDeviceVoList), IamDeviceDo.class);

        this.imagesLabelService.batchDeleteImagesLabel(imagesIdList);
        this.imagesDeviceService.batchDeleteImagesDevice(imagesIdList);

        //查询所有图片
        List<ImagesVo> imagesList = this.imagesMapper.getImagesList(imagesIdList);
        for (ImagesVo imagesVo:imagesList) {
            this.fastDfsClientWrapper.deleteFile(imagesVo.getImagesPath());
        }

        int num = this.imagesMapper.batchDeleteImages(imagesIdList);

        //websockert给设备推消息
        WebSocketServer.sendImagesUpdate(merchantId, iamDeviceDoList);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LabelImagesMobelVo getLabelByImages(LabelImagesMobelBo labelImagesMobel) {
        log.debug("===标签比对图片上行参数===>" + JSONArray.toJSON(labelImagesMobel));
        //判断session
        /*HttpSession session = MySessionContext.getInstance().getSession(labelImagesMobel.getSid());
        if (session == null || session.getAttribute(SessionConstant.CLIENT_SESSION_USER) == null) {
            throw new LoginException("您还未登录设备前台");
        }*/

        //对象转换
        LabelImagesMobelVo labelImagesMobelVo = new LabelImagesMobelVo();
        BeanUtils.copyProperties(labelImagesMobel, labelImagesMobelVo);
        //labelImagesMobelVo.setHeadImages(labelImagesMobel.getHeadImages().getInputStream()+"");

        List<Map<String, Object>> labelByImages = this.imagesMapper.getLabelByImages(labelImagesMobel.getDeviceCode(), labelImagesMobel.getMerchantId(), labelImagesMobel.getLabelStr());
        List<Map<String, Object>> resultList = labelByImages.stream().filter(map -> Integer.valueOf(map.get("count")+"") >= IAMConstant.KEY_MATCHES_NUM).collect(Collectors.toList());
        List<Map<String, Object>> mapList = resultList.stream().limit(IAMConstant.KEY_PUSH_NUM).collect(Collectors.toList());
        mapList.forEach(map -> {
            map.put("imagespath", this.fastDfsClientWrapper.fileHttpFullPath(map.get("imagespath")+""));
        });

        //List<ImagesVo> imagesVoList = JSONArray.parseArray(JSONObject.toJSONString(mapList), ImagesVo.class);
        //是否在有效期内
        List<Map<String, Object>> resultList2 = Lists.newArrayList();
        try {
            resultList2 = ImagesUtil.getImagesListMap(mapList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        labelImagesMobelVo.setImagesList(resultList2);

        Integer merchantId = labelImagesMobel.getMerchantId();
        Date date = new Date();
        List<PushDataBo> pushDataBoList = Lists.newArrayList();
        for (Map<String, Object> map:resultList2) {
            //添加
            PushDataBo pushDataBo = new PushDataBo();
            pushDataBo.setMerchantId(merchantId);
            pushDataBo.setDeviceId(Integer.valueOf(map.get("deviceid")+""));
            pushDataBo.setPushDate(date);
            pushDataBo.setImagesId(Integer.valueOf(map.get("imagesid")+""));
            pushDataBoList.add(pushDataBo);
        }
        if(pushDataBoList.size() > 0){
            this.pushDataService.batchInsert(pushDataBoList);
        }
        //websockert给设备推消息
        WebSocketServer.sendToUser(labelImagesMobelVo, labelImagesMobel);

        log.debug("===标签比对图片下行参数===>" + JSONArray.toJSON(labelImagesMobelVo));
        /*if(mapList.size() <= 0){
            return labelImagesMobelVo;
        }*/
        return labelImagesMobelVo;
    }

    @Override
    public List<ImagesVo> getImagesList(List<Integer> imagesIdList) {
        return this.imagesMapper.getImagesList(imagesIdList);
    }
}