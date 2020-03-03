package com.lace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author centricgateway
 */
@Configuration
public class CorsConfig {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    .addMapping("/**")
                    .allowedMethods("PUT", "DELETE", "POST", "GET", "OPTIONS")
                    .allowedHeaders("X-User-Token", "Authorization", "Content-Type")
                    .allowedOrigins("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            }
        };
    }
}
