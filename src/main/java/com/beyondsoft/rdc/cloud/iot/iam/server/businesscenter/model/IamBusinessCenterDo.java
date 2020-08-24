package com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model;

import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 15:14
 */

@Data
public class IamBusinessCenterDo {

    private Integer id;

    private String merchantName;

    private Integer userId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
