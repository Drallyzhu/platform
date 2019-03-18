package com.cloudsoft.platform.shiro.config;

import com.cloudsoft.common.config.BaseConfig;
import com.cloudsoft.platform.shiro.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends BaseConfig {

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    };


    /**
     * 配置拦截器(项目加载是否添加拦截器)
     * @author lance
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor()).addPathPatterns("/api/**");//拦截api开头的
//        super.addInterceptors(registry);
//
//    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor()).addPathPatterns("/api/**");//拦截api开头的
    }
}
