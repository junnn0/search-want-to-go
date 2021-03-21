package com.junyoung.searchwheretogoapi.properties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.jwt")
@Component
public class JwtProperties {
    private String secretKey;
    private int sessionTime;

    public void setSecretKey(String secretKey) {
        this.secretKey =
                Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
