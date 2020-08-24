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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangningning@beyondsoft.com
 * @version 1.0
 * @date 19-8-12 上午10:18
 */
@Aspect//作用是把当前类标识为一个切面供容器读取
@Component//（把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
@Slf4j
public class ControllerAop {

    @Pointcut("execution(public * com.beyondsoft.rdc.cloud.iot.iam..*.*Controller.*(..))")
    private void controllerMethod() {
    }

    /**
     * aop环绕
     * @param pjp
     * @returnR
     * @throws Throwable
     */
    @Around("controllerMethod()")
    public Object controllerAround(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();
        List<Object> list = Arrays.asList(args);
        //目标方法之前要执行的操作
        log.debug("==环绕日志==methodName:{}=参数list：{}", methodName, list);

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        //获取请求方式
        String method = request.getMethod();
        log.debug("==环绕日志==method:{}", method);
        String uri = request.getRequestURI();
        log.debug("==请求uri==uri：{}", uri);
        Map<String, String[]> paramMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> paramSet = paramMap.entrySet();
        for (Map.Entry entry:paramSet) {
            log.debug("==请求参数==entry-key：{}=entry-calue：{}", entry.getKey(), entry.getValue());
        }

        /*String language = request.getHeader("language");
        log.debug("==Header请求参数==language：{}", language);
        GlobalValue.resetLanguage(language);*/

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        //log.debug("==结果集==result：{}", JSON.toJSONString(result));
        return result;
    }
}