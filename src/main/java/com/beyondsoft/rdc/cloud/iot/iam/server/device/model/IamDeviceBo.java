package com.beyondsoft.rdc.cloud.iot.iam.server.device.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 14:45
 */

@Data
public class IamDeviceBo {

    private Integer id;

    @NotNull
    @Size(max = 16,min = 1,message = "商户名称需要在16字符以内")
    private String deviceName;

    private String deviceLocation;

    private String deviceNumber;

    private Integer merchantId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
