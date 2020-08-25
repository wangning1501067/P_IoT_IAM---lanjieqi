package com.beyondsoft.rdc.cloud.iot.iam.server.push.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/iam/server/flow")
public class FlowController {

    @Autowired
    private FlowService flowService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ObjectRestResponse save(@RequestBody FlowBo flowBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        int i = this.flowService.insertSelective(flowBo);
        restResponse.setData(i);
        return restResponse;
    }

    /**
     * 区域客流
     */
    @GetMapping("/getAreaFlow")
    public ObjectRestResponse getAreaFlow(@RequestParam("merchantId") Integer merchantId,
                                         @RequestParam("startTime") Long startTime,
                                         @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<Map<String, Object>> resultMap = this.flowService.getFlowByArea(merchantId, startTime, endTime);
        restResponse.setData(resultMap);
        return restResponse;
    }

    /**
     * 今日客流曲线
     */
    @GetMapping("/getDateFlow")
    public ObjectRestResponse getDateFlow(@RequestParam("dateType") Integer dateType,
                                          @RequestParam("merchantId") Integer merchantId,
                                          @RequestParam("startTime") Long startTime,
                                          @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<Map<String, Object>> resultMap = this.flowService.getDateFlow(dateType, merchantId, startTime, endTime);
        restResponse.setData(resultMap);
        return restResponse;
    }

    /**
     * 累计客流
     */
    @GetMapping("/getAllFlow")
    public ObjectRestResponse getAllFlow(@RequestParam("merchantId") Integer merchantId,
                                          @RequestParam("startTime") Long startTime,
                                          @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        Map<String, Object> resultMap = this.flowService.getAllFlow(merchantId, startTime, endTime);
        restResponse.setData(resultMap);
        return restResponse;
    }
}
