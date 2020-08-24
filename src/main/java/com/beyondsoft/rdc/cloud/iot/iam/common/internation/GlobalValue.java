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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangningning@beyondsoft.com
 * @version 1.0
 * @date 19-8-12 下午12:20
 */
@Slf4j
public class GlobalValue {
   /*static String language = "zh";

   public static void resetLanguage(String language){
       log.debug("==resetLanguage==language：{}", language);
       if(!StringUtils.isEmpty(language)){
           GlobalValue.language = language;
       }else{
           GlobalValue.language = "zh";
       }
       log.debug("==resetLanguage==GlobalValue.language：{}", GlobalValue.language);
   }*/

    public static String getLanguage(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String  language = request.getHeader("language");
        log.debug("===GlobalValue===language:{}", language);
        if(StringUtils.isEmpty(language)){
            language = "zh";
        }
        //log.debug("===GlobalValue===language2:{}", language);
        return language;
    }
}
