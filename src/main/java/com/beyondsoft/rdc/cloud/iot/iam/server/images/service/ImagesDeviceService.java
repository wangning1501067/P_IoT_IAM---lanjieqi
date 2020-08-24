package com.beyondsoft.rdc.cloud.iot.iam.server.images.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceVo;

import java.util.List;

public interface ImagesDeviceService {
    int deleteByPrimaryKey(Integer id);

    int insert(ImagesDeviceBo record);

    int insertSelective(ImagesDeviceBo record);

    ImagesDeviceVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImagesDeviceBo record);

    int updateByPrimaryKey(ImagesDeviceBo record);

    int batchInsert(List<IamDeviceDo> iamDeviceDoList, Integer imageId);

    int deleteImagesId(Integer imagesId);

    int batchDeleteImagesDevice(List<Integer> imagesIdList);

    List<ImagesDeviceVo> getImagesByDevice(Integer imagesId);

    List<ImagesDeviceVo> getImagesByDeviceBatch(List<Integer> imagesId);

    int deleteDeviceId(List<Integer> deviceIdList);
}