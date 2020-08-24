package com.beyondsoft.rdc.cloud.iot.iam.server.label.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelVo;

import java.util.List;
import java.util.Map;

public interface LabelService {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelBo record);

    int insertSelective(LabelBo record);

    LabelVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelBo record);

    int updateByPrimaryKey(LabelBo record);

    Map<Integer, List<LabelVo>> getList();
}