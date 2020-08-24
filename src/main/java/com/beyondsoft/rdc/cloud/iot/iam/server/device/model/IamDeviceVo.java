package com.beyondsoft.rdc.cloud.iot.iam.server.device.model;

import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 13:41
 */

@Data
public class IamDeviceVo {

    private Integer id;

    private String deviceName;

    private String deviceLocation;

    private String deviceLocationName;

    private String deviceNumber;

    private Integer merchantId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
