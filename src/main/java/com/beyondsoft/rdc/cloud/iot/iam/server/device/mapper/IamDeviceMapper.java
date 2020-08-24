package com.beyondsoft.rdc.cloud.iot.iam.server.device.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 14:29
 */

@Mapper
public interface IamDeviceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(IamDeviceDo record);

    int insertSelective(IamDeviceDo record);

    IamDeviceVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamDeviceDo record);

    int updateByPrimaryKey(IamDeviceDo record);

    int updateByDeviceId(@Param("deviceNumber") String deviceNumber,@Param("status") Integer status,@Param("merchantId") Integer merchantId);

    //集合
    List<IamDeviceVo> getList(String deviceLocation,Integer status,String deviceName);

    List<String> getListArea();

    List<IamDeviceVo> getByAreaList(String deviceLocation);

    List<IamDeviceVo> getByStatusList(Integer status);

    List<IamDeviceVo> getByDeviceNameList(String deviceName);

    IamDeviceVo getByNameAndId(@Param("merchantId") Integer merchantId,@Param("deviceName") String deviceName);

    IamDeviceVo getByNumberAndId(@Param("merchantId") Integer merchantId,@Param("deviceNumber") String deviceNumber);

    IamDeviceVo getDeviceByUserId(@Param("userId") Integer userId,@Param("deviceNumber") String deviceNumber);

    List<IamDeviceVo> getByBusinessId(@Param("merchantId") Integer merchantId);
}
