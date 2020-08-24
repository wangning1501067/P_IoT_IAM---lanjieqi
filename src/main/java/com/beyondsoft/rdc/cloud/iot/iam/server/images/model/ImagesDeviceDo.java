package com.beyondsoft.rdc.cloud.iot.iam.server.images.model;

import lombok.Data;

@Data
public class ImagesDeviceDo {
    private Integer id;

    private Integer imagesId;

    private Integer deviceId;

    private String deviceArea;
}