package com.beyondsoft.rdc.cloud.iot.iam.server.images.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesLabelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImagesLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImagesLabelDo record);

    int insertSelective(ImagesLabelDo record);

    ImagesLabelVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImagesLabelDo record);

    int updateByPrimaryKey(ImagesLabelDo record);

    int batchInsert(@Param("imagesLabelDoList") List<ImagesLabelDo> imagesLabelDoList);

    int deleteImagesId(Integer imagesId);

    int batchDeleteImagesLabel(@Param("imagesIdList") List<Integer> imagesIdList);

    List<ImagesLabelVo> getImagesByLabel(Integer imagesId);
}