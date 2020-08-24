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
package com.beyondsoft.rdc.cloud.iot.iam.common.response;

import java.io.Serializable;

/**
 * 返回结果是对象类型时的模板.
 *
 * @param <T> 结果类型.
 * @date: 2018-01-24
 * @version: 1.0
 * @author: scott.che@beyondsoft.com
 */
public class ObjectRestResponse<T> extends BaseResponse implements Serializable {

    T data;

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public ObjectRestResponse(){

    }

    private ObjectRestResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ObjectRestResponse success(Object data) {
        return new ObjectRestResponse(data);
    }
}
