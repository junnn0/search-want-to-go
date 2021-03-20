package com.junyoung.searchwheretogoapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${allowed.origin}")
    private String allowedOriginUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(allowedOriginUrl)
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name())
                .allowedHeaders(
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CACHE_CONTROL,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                .allowCredentials(true);
    }
}
