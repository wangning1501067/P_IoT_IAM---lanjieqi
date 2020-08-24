package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.label.model.LabelBo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class ImagesBo {
    private Integer id;

    private String imagesPath;

    private String imagesName;

    private Long imagesStartTime;

    private Long imagesEndTime;

    private Integer imagesTimeStart;

    private Date createTime;

    private Date updateTime;

    //图片流
    private MultipartFile images;

    private List<LabelBo> labelList;

    private List<IamDeviceBo> deviceList;

    private Integer merchantId;
}