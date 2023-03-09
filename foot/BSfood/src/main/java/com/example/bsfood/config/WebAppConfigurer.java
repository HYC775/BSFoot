package com.example.bsfood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override

 public void addCorsMappings(CorsRegistry registry) {
   registry.addMapping("/**")
  .allowedOriginPatterns("*")
  .allowCredentials(true)
  .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
  .maxAge(3600);
  }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/image/userAvatar/**").addResourceLocations("file:D:\\bsfootImgs\\userAvatar\\");
         registry.addResourceHandler("/image/footimgs/**").addResourceLocations("file:D:\\bsfootImgs\\footimgs\\");

    }

}
