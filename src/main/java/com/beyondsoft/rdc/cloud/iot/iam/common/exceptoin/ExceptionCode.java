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

package com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin;

/**
 * 通用异常码定义
 *
 * @date: 2019-03-01
 * @version: 1.0
 * @author: wangningning@beyondsoft.com
 */
public interface ExceptionCode {

    /**
     * 异常码，一般异常
     */
    int ALL_CODE = -1;

    /**
     * 登录过滤器
     */
    int LOFIN_FAIL = -100;
}
