package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    private static final String CORS_MAPPING_KEY = "cors.mapping";
    private static final String CORS_ORIGINS_KEY = "cors.origins";
    private static final String CORS_HEADERS_KEY = "cors.headers";
    private static final String CORS_METHODS_KEY = "cors.methods";
    private static final String CORS_MAX_AGE_KEY = "cors.maxAge";
    private static final String CORS_CREDENTIALS_KEY = "cors.credentials";

    private Map<String, Object> corsMap;

    public WebCorsConfig() {
        corsMap = new HashMap<>();
        corsMap.put(CORS_MAPPING_KEY, "/**");
        corsMap.put(CORS_ORIGINS_KEY, "*");
        corsMap.put(CORS_HEADERS_KEY, "*");
        corsMap.put(CORS_METHODS_KEY, "GET,POST,PUT,DELETE,PATCH");
        corsMap.put(CORS_MAX_AGE_KEY, 3600l);
        corsMap.put(CORS_CREDENTIALS_KEY, false);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping(corsMap.get(CORS_MAPPING_KEY).toString())
                .allowedOrigins(corsMap.get(CORS_ORIGINS_KEY).toString().split(","))
                .allowedMethods(corsMap.get(CORS_METHODS_KEY).toString().split(","))
                .allowedHeaders(corsMap.get(CORS_HEADERS_KEY).toString().split(","))
                .allowCredentials(Boolean.valueOf(corsMap.get(CORS_CREDENTIALS_KEY).toString()))
                .maxAge(Long.valueOf(corsMap.get(CORS_MAX_AGE_KEY).toString()));
    }

}
