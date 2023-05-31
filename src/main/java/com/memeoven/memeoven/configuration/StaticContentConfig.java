package com.memeoven.memeoven.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticContentConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/memes/**")
                .addResourceLocations("file:src/main/resources/static/memes/")
                .setCachePeriod(0);

        registry.addResourceHandler("/avatars/**")
                .addResourceLocations("file:src/main/resources/static/avatars/")
                .setCachePeriod(0);
    }
}