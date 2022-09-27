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
        .addResourceLocations("file:D:/shixun/pad-front/target/classes/static/images/");
        //"file:D:/shixun/pad-front/target/classes/static/images/"
    }
}
