package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // Set timeout to 5 minutes (300,000 ms) for async requests to match WebClient
        // timeout
        configurer.setDefaultTimeout(300_000);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Enable CORS for all endpoints to allow frontend communication
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200", "http://localhost:3000", "http://127.0.0.1:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
