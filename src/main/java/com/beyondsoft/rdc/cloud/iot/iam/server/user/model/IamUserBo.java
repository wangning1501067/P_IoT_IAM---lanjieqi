package com.beyondsoft.rdc.cloud.iot.iam.server.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class IamUserBo {
    private Integer id;

    private String username;

    private String password;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}