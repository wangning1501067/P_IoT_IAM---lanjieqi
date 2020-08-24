package com.beyondsoft.rdc.cloud.iot.iam.server.area.model;

import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 16:57
 */

@Data
public class IamAreaBo {

    private Integer id;

    private String areaName;

    private Integer parentId;

    private Integer level;

    private Date createTime;

    private Date updateTime;

    private Integer merchantId;

}
