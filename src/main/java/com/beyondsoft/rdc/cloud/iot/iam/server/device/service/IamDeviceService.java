package com.beyondsoft.rdc.cloud.iot.iam.server.device.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceVo;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 14:32
 */
public interface IamDeviceService {

    int deleteByPrimaryKey(Integer id);

    int insert(IamDeviceBo record);

    int insertSelective(IamDeviceBo record);

    IamDeviceVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamDeviceBo record);

    int updateByDeviceId(Integer merchantId,String deviceNumber,Integer status);

    int updateByPrimaryKey(IamDeviceBo record);

    List<IamDeviceVo> getList(String deviceLocation,Integer status,String deviceName);

    List<String> getListArea();

    List<IamDeviceVo> getByAreaList(String name);

    List<IamDeviceVo> getByStatusList(Integer status);

    List<IamDeviceVo> getByDeviceNameList(String name);

    IamDeviceVo getByNameAndId(Integer id,String name);

    IamDeviceVo getByNumberAndId(Integer id,String number);

    IamDeviceVo getDeviceByUserId(Integer userId, String deviceNumber);

    List<IamDeviceVo> getByBusinessId(Integer id);

}
