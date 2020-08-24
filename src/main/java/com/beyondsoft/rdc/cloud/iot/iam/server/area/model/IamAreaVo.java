package com.beyondsoft.rdc.cloud.iot.iam.server.area.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 16:56
 */

@Data
public class IamAreaVo {

    private Integer id;

    private String areaName;

    private Integer parentId;

    private Integer level;

    private Date createTime;

    private Date updateTime;

    private Integer merchantId;

    private List<IamAreaVo> iamAreaVos;
}
