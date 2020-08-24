package com.beyondsoft.rdc.cloud.iot.iam.server.label.model;

import lombok.Data;

import java.util.Date;

@Data
public class LabelVo {
    private Integer id;

    private String labelName;

    private Integer labelType;

    private Integer labelStatus;

    private Date createTime;

    private Date updateTime;
}