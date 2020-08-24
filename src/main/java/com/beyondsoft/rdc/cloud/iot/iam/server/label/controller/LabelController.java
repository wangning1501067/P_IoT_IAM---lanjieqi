package com.beyondsoft.rdc.cloud.iot.iam.server.label.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/iam/server/label")
@Slf4j
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ObjectRestResponse save(@RequestBody LabelBo labelBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.labelService.insertSelective(labelBo);
            restResponse.setData(num);
        } catch (Exception e) {
            log.error("===标签添加失败===", e);
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
            int num = this.labelService.deleteByPrimaryKey(labelId);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===标签删除失败===", e);
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(e.getCode());
        } catch (Exception e) {
            log.error("===标签删除失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ObjectRestResponse update(@RequestBody LabelBo labelBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int result = this.labelService.updateByPrimaryKeySelective(labelBo);
            restResponse.setData(result);
        } catch (Exception e) {
            log.error("===标签修改失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 查询单个
     */
    @GetMapping("/getOne")
    public ObjectRestResponse getOne(@RequestParam("labelId") Integer labelId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            LabelVo labelVo = this.labelService.selectByPrimaryKey(labelId);
            restResponse.setData(labelVo);
        } catch (Exception e) {
            log.error("===标签查询单个失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 集合
     */
    @GetMapping("/getList")
    public ObjectRestResponse getListStorey() {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        Map<Integer, List<LabelVo>> labelVoMap = this.labelService.getList();
        restResponse.setData(labelVoMap);
        return restResponse;
    }
}