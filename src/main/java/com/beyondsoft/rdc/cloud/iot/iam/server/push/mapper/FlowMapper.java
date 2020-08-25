package com.beyondsoft.rdc.cloud.iot.iam.server.push.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface FlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowDo record);

    int insertSelective(FlowDo record);

    FlowVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowDo record);

    int updateByPrimaryKey(FlowDo record);

    List<Map<String, Object>> getFlowByArea(@Param("merchantId") Integer merchantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<FlowVo> getFlowList(@Param("merchantId") Integer merchantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}