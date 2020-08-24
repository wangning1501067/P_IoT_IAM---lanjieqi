package com.beyondsoft.rdc.cloud.iot.iam.server.device.model;

import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 14:46
 */

@Data
public class IamDeviceDo {

    private Integer id;

    private String deviceName;

    private String deviceLocation;

    private String deviceNumber;

    private Integer merchantId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
