package com.beyondsoft.rdc.cloud.iot.iam.server.user.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserVo;

public interface IamUserService {
    int deleteByPrimaryKey(Integer id);

    int insert(IamUserBo record);

    int insertSelective(IamUserBo record);

    IamUserVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamUserBo record);

    int updateByPrimaryKey(IamUserBo record);

    IamUserVo getOneUser(String username, String password);
}