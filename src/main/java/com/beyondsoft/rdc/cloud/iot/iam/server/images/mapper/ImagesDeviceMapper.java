package com.beyondsoft.rdc.cloud.iot.iam.server.images.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImagesDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImagesDeviceDo record);

    int insertSelective(ImagesDeviceDo record);

    ImagesDeviceVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImagesDeviceDo record);

    int updateByPrimaryKey(ImagesDeviceDo record);

    int batchInsert(@Param("imagesDeviceDoList") List<ImagesDeviceDo> imagesDeviceDoList);

    int deleteImagesId(Integer imagesId);

    int batchDeleteImagesDevice(@Param("imagesIdList") List<Integer> imagesIdList);

    List<ImagesDeviceVo> getImagesByDevice(Integer imagesId);

    List<ImagesDeviceVo> getImagesByDeviceBatch(@Param("imagesIdList") List<Integer> imagesIdList);

    int batchDeleteImagesDeviceByDeviceId(@Param("deviceIdList") List<Integer> deviceIdList);
}