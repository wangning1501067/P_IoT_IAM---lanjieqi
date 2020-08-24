package com.beyondsoft.rdc.cloud.iot.iam.server.images.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.server.images.mapper.ImagesLabelMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesLabelService;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelDo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesLabelServiceImpl implements ImagesLabelService {

    @Autowired
    private ImagesLabelMapper imagesLabelMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.imagesLabelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ImagesLabelBo record) {
        ImagesLabelDo imagesLabelDo = new ImagesLabelDo();
        BeanUtils.copyProperties(record, imagesLabelDo);
        return this.imagesLabelMapper.insert(imagesLabelDo);
    }

    @Override
    public int insertSelective(ImagesLabelBo record) {
        ImagesLabelDo imagesLabelDo = new ImagesLabelDo();
        BeanUtils.copyProperties(record, imagesLabelDo);
        return this.imagesLabelMapper.insertSelective(imagesLabelDo);
    }

    @Override
    public ImagesLabelVo selectByPrimaryKey(Integer id) {
        return this.imagesLabelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ImagesLabelBo record) {
        ImagesLabelDo imagesLabelDo = new ImagesLabelDo();
        BeanUtils.copyProperties(record, imagesLabelDo);
        return this.imagesLabelMapper.updateByPrimaryKeySelective(imagesLabelDo);
    }

    @Override
    public int updateByPrimaryKey(ImagesLabelBo record) {
        ImagesLabelDo imagesLabelDo = new ImagesLabelDo();
        BeanUtils.copyProperties(record, imagesLabelDo);
        return this.imagesLabelMapper.updateByPrimaryKey(imagesLabelDo);
    }

    @Override
    public int batchInsert(List<LabelDo> labelList, Integer imagesId) {
        List<ImagesLabelDo> imagesLabelDoList = Lists.newArrayList();
        for (LabelDo labelDo:labelList) {
            ImagesLabelDo imagesLabelDo = new ImagesLabelDo();
            imagesLabelDo.setImagesId(imagesId);
            imagesLabelDo.setLabelId(labelDo.getId());
            imagesLabelDo.setLabelType(labelDo.getLabelType());

            imagesLabelDoList.add(imagesLabelDo);
        }
        if (CollectionUtils.isEmpty(imagesLabelDoList)) {
            return 0;
        }
        return this.imagesLabelMapper.batchInsert(imagesLabelDoList);
    }

    @Override
    public int deleteImagesId(Integer imagesId) {
        return this.imagesLabelMapper.deleteImagesId(imagesId);
    }

    @Override
    public int batchDeleteImagesLabel(List<Integer> imagesIdList) {
        return this.imagesLabelMapper.batchDeleteImagesLabel(imagesIdList);
    }

    @Override
    public List<ImagesLabelVo> getImagesByLabel(Integer imagesId) {
        return this.imagesLabelMapper.getImagesByLabel(imagesId);
    }

}