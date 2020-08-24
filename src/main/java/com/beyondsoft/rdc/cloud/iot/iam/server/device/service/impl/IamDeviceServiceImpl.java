package com.beyondsoft.rdc.cloud.iot.iam.server.device.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.client.websocket.server.WebSocketServer;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.mapper.IamAreaMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.mapper.IamDeviceMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.service.IamDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.service.PushDataService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 14:32
 */

@Service
@Slf4j
public class IamDeviceServiceImpl implements IamDeviceService {

    @Autowired
    private IamDeviceMapper mapper;

    @Autowired
    private IamAreaMapper iamAreaMapper;

    @Autowired
    private ImagesDeviceService imagesDeviceService;

    @Autowired
    private PushDataService pushDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer id) {
        int num = this.mapper.deleteByPrimaryKey(id);
        List list = new ArrayList<>();
        list.add(id);
        //删除关系表
        this.imagesDeviceService.deleteDeviceId(list);

        //删除推送
        this.pushDataService.batchDeleteByDeviceId(list);
        return num;
    }

    @Override
    public int insert(IamDeviceBo record) {
        IamDeviceDo device = new IamDeviceDo();
        BeanUtils.copyProperties(record, device);
        return this.mapper.insert(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(IamDeviceBo record) {
        IamDeviceVo byNameAndId = this.getByNameAndId(record.getMerchantId(), record.getDeviceName());
        IamDeviceVo byNumberAndId = this.getByNumberAndId(record.getMerchantId(), record.getDeviceNumber());
        //商户下是否存在该设备名称
        if(!ObjectUtils.isEmpty(byNameAndId)){
            throw new GeneralException(InternationEnum.DEVICE_NAME_HAS_EXISTS.getLanguage(GlobalValue.getLanguage()));
        }
        //商户下是否存在该设备编号
        if(!ObjectUtils.isEmpty(byNumberAndId)){
            throw new GeneralException(InternationEnum.KEY_DEVICE_ALREADY_EXISTS.getLanguage(GlobalValue.getLanguage()));
        }
        record.setStatus(1);
        IamDeviceDo device = new IamDeviceDo();
        BeanUtils.copyProperties(record, device);
        log.info("添加设备==> {" + device + "}");
        return this.mapper.insertSelective(device);


    }

    @Override
    public IamDeviceVo selectByPrimaryKey(Integer id) {
        IamDeviceVo iamDeviceVo = this.mapper.selectByPrimaryKey(id);
        /*String deviceLocation = iamDeviceVo.getDeviceLocation();
        *//*for(int i=0; i<split.length; i++){
            IamAreaVo iamAreaVo = iamAreaMapper.selectByPrimaryKey(Integer.valueOf(split[i]));
            String areaName = iamAreaVo.getAreaName();
            stringBuffer.append(areaName);
        }*//*
        List<Integer> intList = getInt(deviceLocation);
        //String areaByAreaName = this.iamAreaMapper.getAreaByAreaName(intList);
        iamDeviceVo.setDeviceLocation(intList.toString());*/
        return iamDeviceVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(IamDeviceBo record) {
        IamDeviceVo deviceVo = this.mapper.selectByPrimaryKey(record.getId());

        if (!deviceVo.getDeviceName().equals(deviceVo.getDeviceName())) {
            IamDeviceVo byNameAndId = this.getByNameAndId(record.getMerchantId(), record.getDeviceName());
            //商户下是否存在该设备名称
            if(!ObjectUtils.isEmpty(byNameAndId)){
                throw new GeneralException(InternationEnum.DEVICE_NAME_HAS_EXISTS.getLanguage(GlobalValue.getLanguage()));
            }
        }
        if (!deviceVo.getDeviceNumber().equals(deviceVo.getDeviceNumber())) {
            IamDeviceVo byNumberAndId = this.getByNumberAndId(record.getMerchantId(), record.getDeviceNumber());
            //商户下是否存在该设备编号
            if (!ObjectUtils.isEmpty(byNumberAndId)) {
                throw new GeneralException(InternationEnum.DEVICE_NOT_EXISTS.getLanguage(GlobalValue.getLanguage()));
            }
        }
        IamDeviceDo device = new IamDeviceDo();
        BeanUtils.copyProperties(record, device);
        device.setUpdateTime(new Date());
        return this.mapper.updateByPrimaryKeySelective(device);
    }

    @Override
        public int updateByDeviceId(Integer merchantId,String deviceNumber,Integer status) {
        return this.mapper.updateByDeviceId(deviceNumber,status,merchantId);

    }

    @Override
    public int updateByPrimaryKey(IamDeviceBo record) {
        IamDeviceDo device = new IamDeviceDo();
        BeanUtils.copyProperties(record, device);
        return this.mapper.updateByPrimaryKey(device);
    }

    @Override
    public List<IamDeviceVo> getList(String deviceLocation,Integer status,String deviceName) {
        List<IamDeviceVo> list = this.mapper.getList(deviceLocation,status,deviceName);
        /*for(IamDeviceVo vo : list){
            boolean b = WebSocketServer.ifDeviceOnline(vo.getMerchantId(), vo.getDeviceNumber());
            if(b){
                vo.setStatus(1);
            }else {
                vo.setStatus(0);
            }
        }*/
        return this.idToName(list);
        //return list;
    }

    @Override
    public List<String> getListArea() {
        return this.mapper.getListArea();
    }

    @Override
    public List<IamDeviceVo> getByAreaList(String name) {
        List<IamDeviceVo> byAreaList = this.mapper.getByAreaList(name);
        return this.idToName(byAreaList);
    }

    @Override
    public List<IamDeviceVo> getByStatusList(Integer status) {
        List<IamDeviceVo> byStatusList = this.mapper.getByStatusList(status);
        return this.idToName(byStatusList);
    }

    @Override
    public List<IamDeviceVo> getByDeviceNameList(String name) {
        List<IamDeviceVo> byDeviceNameList = this.mapper.getByDeviceNameList(name);
        return this.idToName(byDeviceNameList);
    }

    @Override
    public IamDeviceVo getByNameAndId(Integer id, String name) {
        return this.mapper.getByNameAndId(id,name);
    }

    @Override
    public IamDeviceVo getByNumberAndId(Integer id, String number) {
        return this.mapper.getByNumberAndId(id,number);
    }

    @Override
    public IamDeviceVo getDeviceByUserId(Integer userId, String deviceNumber) {
        return this.mapper.getDeviceByUserId(userId, deviceNumber);
    }

    @Override
    public List<IamDeviceVo> getByBusinessId(Integer id) {
        List<IamDeviceVo> list = this.mapper.getByBusinessId(id);
        return this.idToName(list);
    }


    //设备id转设备名称方法提取
    public List<IamDeviceVo> idToName(List<IamDeviceVo> list){
        for(IamDeviceVo vo : list){
            String deviceLocation = vo.getDeviceLocation();
            List<Integer> intList = getInt(deviceLocation);
            if(!StringUtils.isEmpty(deviceLocation)){
                String areaByAreaName = this.iamAreaMapper.getAreaByAreaName(intList);
                //String replace = areaByAreaName.replace(",", "");
                vo.setDeviceLocationName(areaByAreaName);
            }
        }
        return list;
    }

    private List<Integer> getInt(String deviceLocation) {
        String[] locationArray = deviceLocation.split(",");
        List<Integer> intList = Lists.newArrayList();
        for (String s : locationArray) {
            intList.add(Integer.valueOf(s));
        }
        return intList;
    }
}
