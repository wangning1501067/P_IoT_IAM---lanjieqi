package com.beyondsoft.rdc.cloud.iot.iam.client.user.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LabelImagesMobelVo {

    private List<Map<String, Object>> imagesList;

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
}
