package com.beyondsoft.rdc.cloud.iot.iam.server.images.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam/server/images")
@Slf4j
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ObjectRestResponse save(ImagesBo imagesBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.imagesService.insertSelective(imagesBo);
            restResponse.setData(num);
        } catch (GeneralException e1) {
            log.error("===图片添加失败===", e1);
            restResponse.setMessage(e1.getMessageInfo());
            restResponse.setStatus(400);
        } catch (Exception e) {
            log.error("===图片添加失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("merchantId") Integer merchantId, @RequestParam("imagesId") Integer imagesId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.imagesService.deleteByPrimaryKey(merchantId, imagesId);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===图片删除失败===", e);
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(e.getCode());
        } catch (Exception e) {
            log.error("===图片删除失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batchDelete")
    public ObjectRestResponse batchDelete(@RequestParam("merchantId") Integer merchantId, @RequestParam("imagesIdList") List<Integer> imagesIdList) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.imagesService.batchDeleteImages(merchantId, imagesIdList);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===图片批量删除失败===", e);
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(e.getCode());
        } catch (Exception e) {
            log.error("===图片批量删除失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ObjectRestResponse update(ImagesBo imagesBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int result = this.imagesService.updateByPrimaryKeySelective(imagesBo);
            restResponse.setData(result);
        }catch (GeneralException e1) {
            log.error("===图片添加失败===", e1);
            restResponse.setMessage(e1.getMessageInfo());
            restResponse.setStatus(400);
        } catch (Exception e) {
            log.error("===图片修改失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 查询单个
     */
    @GetMapping("/getOne")
    public ObjectRestResponse getOne(@RequestParam("imagesId") Integer imagesId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            ImagesVo imagesVo = this.imagesService.selectByPrimaryKey(imagesId);
            restResponse.setData(imagesVo);
        } catch (Exception e) {
            log.error("===图片查询单个失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 集合
     */
    @GetMapping("/getList")
    public ObjectRestResponse getListStorey(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam("merchantId") Integer merchantId,
            @RequestParam(value = "deviceCode", required = false) String deviceCode,
            @RequestParam(value = "imagesName", required = false) String imagesName) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        PageHelper.startPage(pageNum, pageSize);
        List<ImagesVo> imagesVoList = this.imagesService.getDeviceByImages(merchantId, deviceCode, imagesName, "1");
        PageInfo page = new PageInfo(imagesVoList);
        restResponse.setData(page);
        return restResponse;
    }
}