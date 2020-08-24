package com.beyondsoft.rdc.cloud.iot.iam.server.images.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImagesDo record);

    int insertSelective(ImagesDo record);

    ImagesVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImagesDo record);

    int updateByPrimaryKey(ImagesDo record);

    List<ImagesVo> getDeviceByImages(@Param("merchantId") Integer merchantId, @Param("deviceCode") String deviceCode, @Param("imagesName") String imagesName);

    int batchDeleteImages(@Param("imagesIdList") List<Integer> imagesIdList);

    List<Map<String, Object>> getLabelByImages(@Param("deviceName") String deviceName, @Param("merchantId") Integer merchantId, @Param("labelNameList") List<String> labelNameList);

    List<ImagesVo> getImagesList(@Param("imagesIdList") List<Integer> imagesIdList);

    List<ImagesVo> getImagesBymerchantId(@Param("merchantId") Integer merchantId);
}