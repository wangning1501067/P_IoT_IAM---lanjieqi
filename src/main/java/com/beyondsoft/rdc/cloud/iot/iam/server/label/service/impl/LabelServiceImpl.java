package com.beyondsoft.rdc.cloud.iot.iam.server.label.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.server.label.mapper.LabelMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.service.LabelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.labelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(LabelBo record) {
        LabelDo label = new LabelDo();
        BeanUtils.copyProperties(record, label);
        return this.labelMapper.insert(label);
    }

    @Override
    public int insertSelective(LabelBo record) {
        LabelDo label = new LabelDo();
        BeanUtils.copyProperties(record, label);
        return this.labelMapper.insertSelective(label);
    }

    @Override
    public LabelVo selectByPrimaryKey(Integer id) {
        return this.labelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(LabelBo record) {
        LabelDo label = new LabelDo();
        BeanUtils.copyProperties(record, label);
        return this.labelMapper.updateByPrimaryKeySelective(label);
    }

    @Override
    public int updateByPrimaryKey(LabelBo record) {
        LabelDo label = new LabelDo();
        BeanUtils.copyProperties(record, label);
        return this.labelMapper.updateByPrimaryKey(label);
    }

    @Override
    public Map<Integer, List<LabelVo>> getList() {
        List<LabelVo> labelVoList = this.labelMapper.getList();
        Map<Integer, List<LabelVo>> collect = labelVoList.stream().collect(Collectors.groupingBy(labelVo -> labelVo.getLabelType()));
        return collect;
    }
}