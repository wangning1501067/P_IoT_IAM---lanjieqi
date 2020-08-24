package com.beyondsoft.rdc.cloud.iot.iam.server.images.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.mapper.ImagesDeviceMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesDeviceVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesDeviceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesDeviceServiceImpl implements ImagesDeviceService {

    @Autowired
    private ImagesDeviceMapper imagesDeviceMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.imagesDeviceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ImagesDeviceBo record) {
        ImagesDeviceDo imagesDeviceDo = new ImagesDeviceDo();
        BeanUtils.copyProperties(record, imagesDeviceDo);
        return this.imagesDeviceMapper.insert(imagesDeviceDo);
    }

    @Override
    public int insertSelective(ImagesDeviceBo record) {
        ImagesDeviceDo imagesDeviceDo = new ImagesDeviceDo();
        BeanUtils.copyProperties(record, imagesDeviceDo);
        return this.imagesDeviceMapper.insertSelective(imagesDeviceDo);
    }

    @Override
    public ImagesDeviceVo selectByPrimaryKey(Integer id) {
        return this.imagesDeviceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ImagesDeviceBo record) {
        ImagesDeviceDo imagesDeviceDo = new ImagesDeviceDo();
        BeanUtils.copyProperties(record, imagesDeviceDo);
        return this.imagesDeviceMapper.updateByPrimaryKeySelective(imagesDeviceDo);
    }

    @Override
    public int updateByPrimaryKey(ImagesDeviceBo record) {
        ImagesDeviceDo imagesDeviceDo = new ImagesDeviceDo();
        BeanUtils.copyProperties(record, imagesDeviceDo);
        return this.imagesDeviceMapper.updateByPrimaryKey(imagesDeviceDo);
    }

    @Override
    public int batchInsert(List<IamDeviceDo> iamDeviceDoList, Integer imagesId) {
        List<ImagesDeviceDo> imagesDeviceDoList = Lists.newArrayList();
        for (IamDeviceDo deviceDo:iamDeviceDoList) {
            ImagesDeviceDo imagesDeviceDo = new ImagesDeviceDo();
            imagesDeviceDo.setImagesId(imagesId);
            imagesDeviceDo.setDeviceId(deviceDo.getId());
            imagesDeviceDo.setDeviceArea(deviceDo.getDeviceLocation());

            imagesDeviceDoList.add(imagesDeviceDo);
        }
        if (CollectionUtils.isEmpty(imagesDeviceDoList)) {
            return 0;
        }
        return this.imagesDeviceMapper.batchInsert(imagesDeviceDoList);
    }

    @Override
    public int deleteImagesId(Integer imagesId) {
        return this.imagesDeviceMapper.deleteImagesId(imagesId);
    }

    @Override
    public int batchDeleteImagesDevice(List<Integer> imagesIdList) {
        return this.imagesDeviceMapper.batchDeleteImagesDevice(imagesIdList);
    }

    @Override
    public List<ImagesDeviceVo> getImagesByDevice(Integer imagesId) {
        return this.imagesDeviceMapper.getImagesByDevice(imagesId);
    }

    @Override
    public List<ImagesDeviceVo> getImagesByDeviceBatch(List<Integer> imagesId) {
        return this.imagesDeviceMapper.getImagesByDeviceBatch(imagesId);
    }

    @Override
    public int deleteDeviceId(List<Integer> deviceIdList) {
        return this.imagesDeviceMapper.batchDeleteImagesDeviceByDeviceId(deviceIdList);
    }


}