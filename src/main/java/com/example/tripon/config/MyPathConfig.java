package com.example.tripon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPathConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("맵핑해야할url적기").addResourceLocations("실제경로");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///c:/kdt/upload/tripon/");
    }

}