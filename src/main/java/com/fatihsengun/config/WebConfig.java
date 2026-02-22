package com.fatihsengun.config;

import com.fatihsengun.service.RateLimitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitingService rateLimitingService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);


    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // This applies your rate limiter ONLY to the wallet endpoints
        registry.addInterceptor(rateLimitingService)
                .addPathPatterns(
                        "/rest/api/wallet/**",
                        "/authenticate",
                        "/register",
                        "/refreshToken",
                        "/rest/api/product/filter");
    }

}
