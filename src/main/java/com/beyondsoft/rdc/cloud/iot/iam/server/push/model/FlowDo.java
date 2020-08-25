package com.beyondsoft.rdc.cloud.iot.iam.server.push.model;

import lombok.Data;

import java.util.Date;

@Data
public class FlowDo {
    private Integer id;

    private Integer merchantId;

    private Integer menNum;

    private Integer deviceId;

    private Date createTime;

    private Date updateTime;

    private Date pushDate;
}