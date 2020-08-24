package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import lombok.Data;

@Data
public class ImagesDeviceBo {
    private Integer id;

    private Integer imagesId;

    private Integer deviceId;

    private String deviceArea;
}