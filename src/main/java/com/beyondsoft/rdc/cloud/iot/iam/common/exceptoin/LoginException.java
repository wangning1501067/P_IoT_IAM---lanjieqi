/*
 * Copyright (c) 2018. Beyondsoft Corporation. All Rights Reserved.
 *
 * BEYONDSOFT CONFIDENTIALITY
 *
 * The information in this file is confidential and privileged from Beyondsoft Corporation, which is intended only for
 * use solely by Beyondsoft Corporation. Disclosure, copying, distribution, or use of the contents of this file by
 * persons other than Beyondsoft Corporation is strictly prohibited and may violate applicable laws.
 */

package com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用异常定义
 *
 * @date: 2018-10-19
 * @version: 1.0
 * @author: robin.luo@beyondsoft.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 9140496207575638316L;

    /**
     * 异常码，遵循Http状态码规范.
     */
    private int code = ExceptionCode.LOFIN_FAIL;

    /**
     * 信息内容，用于辅助调试.
     */
    private String messageInfo;

    /**
     * 构造函数.
     *
     * @param messageInfo message.
     */
    public LoginException(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    /**
     * 构造函数.
     *
     * @param code 错误码.
     * @param messageInfo message.
     */
    public LoginException(int code, String messageInfo) {
        this.code = code;
        this.messageInfo = messageInfo;
    }

}
