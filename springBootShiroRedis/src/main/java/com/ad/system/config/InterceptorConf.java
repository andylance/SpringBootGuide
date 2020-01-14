package com.ad.system.config;

import com.ad.system.interceptor.LoginBackInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConf implements WebMvcConfigurer {
    @Autowired
    private LoginBackInterceptor loginBackInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(loginBackInterceptor);
        registration.addPathPatterns("/**/login*");
    }
}
