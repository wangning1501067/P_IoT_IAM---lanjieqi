package com.beyondsoft.rdc.cloud.iot.iam.demo.model;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String pass;

    private String nickname;
}