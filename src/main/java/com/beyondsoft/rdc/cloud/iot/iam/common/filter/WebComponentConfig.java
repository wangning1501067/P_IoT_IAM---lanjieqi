package com.beyondsoft.rdc.cloud.iot.iam.common.filter;

import com.beyondsoft.rdc.cloud.iot.iam.common.cors.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebComponentConfig {
    /*@Bean
    public FilterRegistrationBean someFilterRegistration1() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter( new SessionFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        registration.setOrder(2);//order的数值越小 则优先级越高
        return registration;
    }*/

    @Bean
    public FilterRegistrationBean someFilterRegistration2() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter( new CorsFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        registration.setOrder(1);//order的数值越小 则优先级越高
        return registration;
    }

}
