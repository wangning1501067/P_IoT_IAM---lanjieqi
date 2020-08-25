package com.beyondsoft.rdc.cloud.iot.iam.server.push.model;

import lombok.Data;

import java.util.Date;

@Data
public class FlowBo {
    private Integer id;

    private Integer merchantId;

    private Integer menNum;

    private Integer deviceId;

    private Long createTime;

    private Long updateTime;

    private Long pushDate;
}