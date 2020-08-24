package com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 12:36
 */

@Mapper
public interface IamBusinessCenterMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(IamBusinessCenterDo record);

    int insertSelective(IamBusinessCenterDo record);

    IamBusinessCenterVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamBusinessCenterDo record);

    int updateByPrimaryKey(IamBusinessCenterDo record);

    List<IamBusinessCenterVo> getList(Integer userId);

    IamBusinessCenterVo getByBusinessId(Integer userId);

}
