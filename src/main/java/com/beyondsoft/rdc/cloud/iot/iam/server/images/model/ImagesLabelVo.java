package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import lombok.Data;

@Data
public class ImagesLabelVo {
    private Integer id;

    private Integer imagesId;

    private Integer labelId;

    private Integer labelType;

    private String labelName;
}