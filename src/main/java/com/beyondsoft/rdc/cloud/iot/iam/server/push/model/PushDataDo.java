package com.beyondsoft.rdc.cloud.iot.iam.server.push.model;

import lombok.Data;

import java.util.Date;

@Data
public class PushDataDo {
    private Integer id;

    private Integer userId;

    private Integer merchantId;

    private Integer deviceId;

    private Date createTime;

    private Date updateTime;

    private Date pushDate;

    private Integer imagesId;
}