package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImagesVo {
    private Integer id;

    private String imagesPath;

    private String imagesName;

    private Date imagesStartTime;

    private Date imagesEndTime;

    private Integer imagesTimeStart;

    private Date createTime;

    private Date updateTime;

    private List<ImagesLabelVo> labelVoList;

    private List<ImagesDeviceVo> deviceVoList;

    private Integer merchantId;
}