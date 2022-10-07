package com.pad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UrlConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("/img/**")
        .addResourceLocations("file:D:/QQPCmgr/Desktop/12shixun/pad-front/src/main/resources/static/images/");

        /*file:D:/QQPCmgr/Desktop/12shixun/pad-front/target/classes/static/images/*/
    }
}
