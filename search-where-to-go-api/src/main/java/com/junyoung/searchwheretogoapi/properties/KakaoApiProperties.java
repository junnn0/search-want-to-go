package com.junyoung.searchwheretogoapi.properties;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "openapi.kakao")
@Component
public class KakaoApiProperties {
  private String baseUrl;
  private String apiKey;
  private Map<String, String> api;
}
