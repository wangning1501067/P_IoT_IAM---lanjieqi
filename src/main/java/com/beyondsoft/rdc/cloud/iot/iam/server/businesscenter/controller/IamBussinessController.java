package com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.service.IamBusinessCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author shkstart
 * @create 2020-07-28 13:08
 */

@RestController
@RequestMapping("/iam/server/bussiness")
@Slf4j
public class IamBussinessController {

    @Autowired
    private IamBusinessCenterService service;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ObjectRestResponse save(@RequestBody @Valid IamBusinessCenterBo iamBusinessCenterBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.service.insertSelective(iamBusinessCenterBo);
            restResponse.setData(num);
        } catch (Exception e) {
            log.error("===商户添加失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("labelId") Integer labelId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.service.deleteByPrimaryKey(labelId);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===商户删除失败===", e);
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(e.getCode());
        } catch (Exception e) {
            log.error("===商户删除失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse update(@RequestBody IamBusinessCenterBo iamBusinessCenterBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int result = this.service.updateByPrimaryKeySelective(iamBusinessCenterBo);
            restResponse.setData(result);
        } catch (Exception e) {
            log.error("===商户修改失败===", e);
            //restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 查询单个
     */
    @GetMapping("/getOne")
    public ObjectRestResponse getOne(@RequestParam("merchantId") Integer merchantId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            IamBusinessCenterVo iamBusinessCenterVo = this.service.selectByPrimaryKey(merchantId);
            restResponse.setData(iamBusinessCenterVo);
        } catch (Exception e) {
            log.error("===商户查询单个失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 集合
     */
    @GetMapping("/getList")
    public ObjectRestResponse getListStorey(@RequestParam("userId") Integer userId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamBusinessCenterVo> list = this.service.getList(userId);
        restResponse.setData(list);
        return restResponse;
    }


    /**
     * 根据用户id获取商户id
     * @param userId
     * @return
     */
    @GetMapping("/getByBusinessId")
    public ObjectRestResponse getByBusinessId(@RequestParam("userId") Integer userId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            IamBusinessCenterVo byBusinessId = this.service.getByBusinessId(userId);
            restResponse.setData(byBusinessId);
        } catch (Exception e) {
            log.error("===商户查询单个失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

}
