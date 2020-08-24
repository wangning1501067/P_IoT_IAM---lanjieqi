package com.beyondsoft.rdc.cloud.iot.iam.server.images.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelDo;

import java.util.List;

public interface ImagesLabelService {
    int deleteByPrimaryKey(Integer id);

    int insert(ImagesLabelBo record);

    int insertSelective(ImagesLabelBo record);

    ImagesLabelVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImagesLabelBo record);

    int updateByPrimaryKey(ImagesLabelBo record);

    int batchInsert(List<LabelDo> labelList, Integer imagesId);

    int deleteImagesId(Integer imagesId);

    int batchDeleteImagesLabel(List<Integer> imagesIdList);

    List<ImagesLabelVo> getImagesByLabel(Integer imagesId);
}