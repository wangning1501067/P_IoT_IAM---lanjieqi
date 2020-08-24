package com.beyondsoft.rdc.cloud.iot.iam.server.push.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataVo;

import java.util.List;
import java.util.Map;

public interface PushDataService {
    int deleteByPrimaryKey(Integer id);

    int insert(PushDataBo record);

    int batchInsert(List<PushDataBo> pushDataBoList);

    int insertSelective(PushDataBo record);

    PushDataVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushDataBo record);

    int updateByPrimaryKey(PushDataBo record);

    Map<String, Object> getPushDataByData(Long pushTime, Integer marchentId);

    Map<String, Object> getDeviceAll(Integer marchentId);

    List<Map<String, Object>> getPushChaert(Integer deviceId, Integer merchantId, Long startTime, Long endTime);

    List<Map<String, Object>> getAdvertList(Integer merchantId, Long startTime, Long endTime);

    List<Map<String, Object>> getDeviceList(Integer merchantId, Long startTime, Long endTime);

    int batchDeleteByDeviceId(List<Integer> deviceIdList);
}