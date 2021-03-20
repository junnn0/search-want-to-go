package com.junyoung.searchwheretogoapi.config;

import com.junyoung.searchwheretogoapi.filter.JwtTokenFilter;
import com.junyoung.searchwheretogoapi.repository.UserRepository;
import com.junyoung.searchwheretogoapi.service.auth.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
  @Bean
  public JwtTokenFilter jwtTokenFilter(UserRepository userRepository, JwtService jwtService) {
    return new JwtTokenFilter(userRepository, jwtService);
  }
}
