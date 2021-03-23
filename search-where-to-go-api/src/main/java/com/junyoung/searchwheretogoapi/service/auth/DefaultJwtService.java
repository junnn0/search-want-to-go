package com.junyoung.searchwheretogoapi.service.auth;

import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultJwtService implements JwtService {

  private final JwtProperties jwtProperties;

  @Override
  public String toToken(User user) {
    return Jwts.builder()
        .setSubject(user.getUserId())
        .setExpiration(expireTimeFromNow())
        .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
        .compact();
  }

  @Override
  public Optional<String> getUserIdFromToken(String token) {
    try {
      Jws<Claims> claimsJws =
          Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
      return Optional.ofNullable(claimsJws.getBody().getSubject());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private Date expireTimeFromNow() {
    return new Date(System.currentTimeMillis() + jwtProperties.getSessionTime() * 1000L);
  }
}
