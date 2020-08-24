package com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 15:14
 */

@Data
public class IamBusinessCenterBo {

    private Integer id;


    @Size(max = 15,min = 1,message = "商户名称需要在15字符以内")
    //@Pattern(regexp = "/^[\\u4e00-\\u9fffa-zA-Z]{1,15}$/")
    private String merchantName;

    private Integer userId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
