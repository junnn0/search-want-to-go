package com.junyoung.searchwheretogoapi.service.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import com.junyoung.searchwheretogoapi.model.data.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultJwtService implements JwtService {
    private final String secret;
    private final int sessionTime;

    @Autowired
    public DefaultJwtService(@Value("${spring.jwt.secret-key}") String secretKey,
                             @Value("${spring.jwt.session-time}") int sessionTime) {
        this.secret = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        this.sessionTime = sessionTime;
    }

    @Override
    public String toToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .setExpiration(expireTimeFromNow())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public Optional<String> getSubFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return Optional.ofNullable(claimsJws.getBody().getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000L);
    }
}
