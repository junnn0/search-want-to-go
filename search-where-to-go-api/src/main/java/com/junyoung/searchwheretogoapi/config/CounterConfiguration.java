package com.junyoung.searchwheretogoapi.config;

import com.junyoung.searchwheretogoapi.service.place.DefaultMapSearchCounter;
import com.junyoung.searchwheretogoapi.service.place.SearchCounter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfiguration {

    @ConditionalOnMissingBean(SearchCounter.class)
    @Bean
    public DefaultMapSearchCounter searchCounter() {
        return new DefaultMapSearchCounter();
    }
}
