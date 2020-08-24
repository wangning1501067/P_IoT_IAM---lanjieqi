package com.beyondsoft.rdc.cloud.iot.iam.demo.service;

import com.beyondsoft.rdc.cloud.iot.iam.demo.model.User;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
