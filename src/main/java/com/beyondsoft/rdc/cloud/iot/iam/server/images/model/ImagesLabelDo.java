package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import lombok.Data;

@Data
public class ImagesLabelDo {
    private Integer id;

    private Integer imagesId;

    private Integer labelId;

    private Integer labelType;
}