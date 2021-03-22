package com.junyoung.searchwheretogoapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager();
    }
}
