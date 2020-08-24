package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelDo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImagesDo {
    private Integer id;

    private String imagesPath;

    private String imagesName;

    private Date imagesStartTime;

    private Date imagesEndTime;

    private Integer imagesTimeStart;

    private Date createTime;

    private Date updateTime;

    private List<LabelDo> labelList;

    private List<IamDeviceDo> deviceList;

    private Integer merchantId;
}