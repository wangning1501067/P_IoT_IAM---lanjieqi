package com.beyondsoft.rdc.cloud.iot.iam.client.user.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LabelImagesMobelBo {
    //一,性别，二，表情，三，年龄
    private List<String> labelStr;

    private Integer merchantId;

    private String deviceCode;

    /**
     * 设备截取图片
     */
    private String headImages;

    /**
     * 人脸坐标点
     */
    private List<Map<String, Object>> facePointList;

    /**
     * 特征坐标点
     */
    private List<Map<String, Object>> standardPointList;

    private String sid;
}
