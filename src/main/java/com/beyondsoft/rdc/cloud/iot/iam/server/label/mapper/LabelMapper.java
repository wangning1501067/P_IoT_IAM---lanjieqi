package com.beyondsoft.rdc.cloud.iot.iam.server.label.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelDo record);

    int insertSelective(LabelDo record);

    LabelVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelDo record);

    int updateByPrimaryKey(LabelDo record);

    List<LabelVo> getList();
}