package inhatc.project.tap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Value("${spring.servlet.multipart.location}")
//    String location;
//    @Value("${spring.servlet.multipart1.location}")
//    String location1;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/C:/TAP/tap_project/src/main/resources/static/img/");
        registry.addResourceHandler("/img/studentImg/**")
                .addResourceLocations("file:/C:/TAP/tap_project/src/main/resources/static/img/shopImg/");
    }
}