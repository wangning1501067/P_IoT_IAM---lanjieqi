package com.beyondsoft.rdc.cloud.iot.iam.server.user.model;

import lombok.Data;

@Data
public class LoginModel {

    private String username;

    private String password;

    private String deviceCode;
    /**
     * 测试代码提交
     */
}