/*
 * Copyright (c) 2018. Beyondsoft Corporation.
 * All Rights Reserved.
 *
 * BEYONDSOFT CONFIDENTIALITY
 *
 * The information in this file is confidential and privileged from Beyondsoft Corporation,
 * which is intended only for use solely by Beyondsoft Corporation.
 * Disclosure, copying, distribution, or use of the contents of this file by persons other than Beyondsoft Corporation
 * is strictly prohibited and may violate applicable laws.
 */

package com.beyondsoft.rdc.cloud.iot.iam.common.internation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangningning@beyondsoft.com
 * @version 1.0
 * @date 19-8-12 上午11:14
 */
@Slf4j
public enum InternationEnum {

    SUCCESS("成功", "Success"),
    SYSTEM_ERROR("系统错误", "System error"),

    //common
    KEY_FILE_NOT_EXIST_ERROR("文件不存在", "File does not exist"),

    //NOCV
    KEY_START_DATE_NULL("开始时间不能为空", "Start time cannot be empty"),
    KEY_END_DATE_NULL("结束时间不能为空", "End time cannot be empty"),

    //登录
    KEY_USERNAME_ERROR("该账号不存在", "User name error"),
    KEY_PASSWORD_ERROR("密码错误", "Password error"),
    KEY_USER_DELETE_FAIL_ERROR("删除失败！", "Delete failed!"),
    KEY_USER_DELETE_SUCCESS_ERROR("删除成功！", "Deleted successfully! "),

    // device
    DEVICE_NAME_HAS_EXISTS("设备名称已存在", "Device name already exists"),
    DEVICE_SERIAL_NUMVER_HAS_EXISTS("设备序列号已存在", "Device serial number already exists"),
    DEVICE_ALREADY_EXISTS("您输入的设备编号已在别处登录", "Device serial number already exists"),

    IMAGES_NAME_HAS_EXISTS("内容名称已存在", "Images name already exists"),

    DEVICE_NOT_EXISTS("设备编号不存在", "Equipment number does not exist"),
    KEY_DEVICE_ALREADY_EXISTS("设备编号已存在", "Equipment number does not exist"),

    //area
    AREAS_ARE_BEING_USED("区域正在被使用", "Areas are being used"),
    THE_REGION_NAME_ALREADY_EXISTS("同级已存在该区域名称", "The region name already exists"),

    END("","");

    private String zhName;

    private String enName;

    InternationEnum(String zhName, String enName) {
        this.zhName = zhName;
        this.enName = enName;
    }

    public String getZhName() {
        return zhName;
    }

    public String getEnName() {
        return enName;
    }

    public static InternationEnum getInternationEnum(String zhName) {
        for (InternationEnum internationEnum : InternationEnum.values()) {
            //log.debug("==枚举==internationEnum：{}", internationEnum.getZhName());
            if (zhName.equals(internationEnum.getZhName())) {
                return internationEnum;
            }
        }
        return null;
    }

    public String getLanguage(String language){
        String str = new String();
        switch (language){
            case "zh" : {
                str = this.getZhName();
                break;
            }
            case "en" : {
                str = this.getEnName();
                break;
            }
            default:{
                str = this.getZhName();
                break;
            }
        }

        return str;
    }
}
