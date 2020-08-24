package com.beyondsoft.rdc.cloud.iot.iam.server.user.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IamUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IamUserDo record);

    int insertSelective(IamUserDo record);

    IamUserVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamUserDo record);

    int updateByPrimaryKey(IamUserDo record);

    IamUserVo getOneUser(@Param("username") String username);
}