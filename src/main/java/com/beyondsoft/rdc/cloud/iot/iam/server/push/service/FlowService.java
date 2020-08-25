package com.beyondsoft.rdc.cloud.iot.iam.server.push.service;

import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FlowService {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowBo record);

    int insertSelective(FlowBo record);

    FlowVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowBo record);

    int updateByPrimaryKey(FlowBo record);

    List<Map<String, Object>> getFlowByArea(Integer merchantId, Long startTime, Long endTime);

    List<Map<String, Object>> getDateFlow(Integer dateType, Integer merchantId, Long startTime, Long endTime);

    Map<String, Object> getAllFlow(Integer merchantId, Long startTime, Long endTime);

    List<FlowVo> getFlowList(Integer merchantId, Date startTime, Date endTime);
}