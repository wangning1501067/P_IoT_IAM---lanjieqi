package com.beyondsoft.rdc.cloud.iot.iam.common.listener;

import org.apache.catalina.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 添加SessionListener到系统配置中
 */
@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter {
    //注册session监听器;
    @Bean
    public ServletListenerRegistrationBean<MySessionListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<MySessionListener> slrBean = new ServletListenerRegistrationBean<MySessionListener>();
        slrBean.setListener(new MySessionListener());
        return slrBean;
    }

}
