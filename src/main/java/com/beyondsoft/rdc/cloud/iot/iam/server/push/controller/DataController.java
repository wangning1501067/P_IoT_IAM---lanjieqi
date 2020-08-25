package com.beyondsoft.rdc.cloud.iot.iam.server.push.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.service.PushDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/iam/server/data")
public class DataController {

    @Autowired
    private PushDataService pushDataService;

    /**
     * 设备总数（台/个）
     */
    @GetMapping("/getDeviceAllNum")
    public ObjectRestResponse getDeviceAllNum(@RequestParam("merchantId") Integer merchantId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        Map<String, Object> map = this.pushDataService.getDeviceAll(merchantId);
        restResponse.setData(map);
        return restResponse;
    }

    /**
     * 今日推送广告（次）
     */
    @GetMapping("/getPushNum")
    public ObjectRestResponse getPushNum(@RequestParam("pushTime") Long pushTime, @RequestParam("merchantId") Integer merchantId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        Map<String, Object> map = this.pushDataService.getPushDataByData(pushTime, merchantId);
        restResponse.setData(map);
        return restResponse;
    }

    /**
     * 推送广告趋势图
     */
    @GetMapping("/getPushChart")
    public ObjectRestResponse getPushNum(@RequestParam("deviceId") Integer deviceId,
                                         @RequestParam("merchantId") Integer merchantId,
                                         @RequestParam("startTime") Long startTime,
                                        @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<Map<String, Object>> dayMap = this.pushDataService.getPushChaert(deviceId, merchantId, startTime, endTime);
        restResponse.setData(dayMap);
        return restResponse;
    }

    /**
     * 推送广告总排行TOP5
     */
    @GetMapping("/getAdvertList")
    public ObjectRestResponse getAdvertList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                            @RequestParam("merchantId") Integer merchantId,
                                         @RequestParam("startTime") Long startTime,
                                         @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultMapList = this.pushDataService.getAdvertList(merchantId, startTime, endTime);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(resultMapList);
        restResponse.setData(pageInfo);
        return restResponse;
    }

    /**
     * 推送广告设备总排行TOP5
     */
    @GetMapping("/getDeviceList")
    public ObjectRestResponse getDeviceList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                            @RequestParam("merchantId") Integer merchantId,
                                         @RequestParam("startTime") Long startTime,
                                         @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultMapList = this.pushDataService.getDeviceList(merchantId, startTime, endTime);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(resultMapList);
        restResponse.setData(pageInfo);
        return restResponse;
    }

    /**
     * 推送广告性别统计
     */
    @GetMapping("/getSexData")
    public ObjectRestResponse getSexData(@RequestParam("labelType") Integer labelType,
                                         @RequestParam("merchantId") Integer merchantId,
                                            @RequestParam("startTime") Long startTime,
                                            @RequestParam("endTime") Long endTime) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<Map<String, Object>> resultMap = this.pushDataService.getSexData(labelType, merchantId, startTime, endTime);
        restResponse.setData(resultMap);
        return restResponse;
    }
}
