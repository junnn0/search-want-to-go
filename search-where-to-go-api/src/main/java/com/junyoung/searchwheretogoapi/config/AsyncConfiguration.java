package com.junyoung.searchwheretogoapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfiguration {
    @Bean
    public ThreadPoolTaskExecutor executorService() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(300);
        threadPoolTaskExecutor.setMaxPoolSize(500);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        return threadPoolTaskExecutor;
    }
}
