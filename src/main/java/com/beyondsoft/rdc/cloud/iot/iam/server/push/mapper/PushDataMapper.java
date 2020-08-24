package com.beyondsoft.rdc.cloud.iot.iam.server.push.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface PushDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PushDataDo record);

    int batchInsert(@Param("pushDataBoList") List<PushDataBo> pushDataBoList);

    int insertSelective(PushDataDo record);

    PushDataVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushDataDo record);

    int updateByPrimaryKey(PushDataDo record);

    Map<String, Object> getPushDataByData(@Param("pushDate") Date pushDate, @Param("marchentId") Integer marchentId);

    List<PushDataVo> getPushChaert(@Param("deviceId") Integer deviceId, @Param("merchantId") Integer merchantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> getAdvertList(@Param("merchantId") Integer merchantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> getDeviceList(@Param("merchantId") Integer merchantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int batchDeleteByDeviceId(@Param("deviceIdList") List<Integer> deviceIdList);
}