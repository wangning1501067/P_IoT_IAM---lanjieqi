package com.beyondsoft.rdc.cloud.iot.iam.server.area.model;

import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-07-28 16:58
 */
@Data
public class IamAreaDo {

    private Integer id;

    private String areaName;

    private Integer parentId;

    private Integer level;

    private Date createTime;

    private Date updateTime;

    private Integer merchantId;
}
