package com.beyondsoft.rdc.cloud.iot.iam.server.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class IamUserVo {
    private Integer id;

    private String username;

    private String password;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    //服务端websocket地址
    private String websocketPath;

    //商户ID
    private Integer merchantId;

    private String sid;
}