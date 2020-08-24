package com.beyondsoft.rdc.cloud.iot.iam.server.images.service;

import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelBo;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesVo;

import java.util.List;

public interface ImagesService {
    int deleteByPrimaryKey(Integer merchantId, Integer id);

    int insert(ImagesBo record);

    int insertSelective(ImagesBo record);

    ImagesVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImagesBo record);

    int updateByPrimaryKey(ImagesBo record);

    List<ImagesVo> getDeviceByImages(Integer merchantId, String deviceCode, String imagesName, String type);

    int batchDeleteImages(Integer merchantId, List<Integer> imagesIdList);

    LabelImagesMobelVo getLabelByImages(LabelImagesMobelBo labelImagesMobel);

    List<ImagesVo> getImagesList(List<Integer> imagesIdList);
}