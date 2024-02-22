package com.nextitproject.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry corsRegistry) {
            corsRegistry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        //.allowedOriginPatterns("*")
                        .allowedMethods("GET","POST","OPTIONS","DELETE","PUT")
                        //.maxAge(3600L)
                        .allowedHeaders("*")
                        //.exposedHeaders("Authorization")
                        .allowCredentials(true);

        }
    };
}
}