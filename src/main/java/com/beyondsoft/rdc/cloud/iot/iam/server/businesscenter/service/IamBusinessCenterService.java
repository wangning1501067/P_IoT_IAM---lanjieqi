package com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterVo;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 12:36
 */
public interface IamBusinessCenterService {

    int deleteByPrimaryKey(Integer id);

    int insert(IamBusinessCenterBo record);

    int insertSelective(IamBusinessCenterBo record);

    IamBusinessCenterVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamBusinessCenterBo record);

    int updateByPrimaryKey(IamBusinessCenterBo record);

    List<IamBusinessCenterVo> getList(Integer userId);

    IamBusinessCenterVo getByBusinessId(Integer userId);

}
