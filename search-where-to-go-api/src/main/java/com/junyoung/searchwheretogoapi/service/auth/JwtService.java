package com.junyoung.searchwheretogoapi.service.auth;

import com.junyoung.searchwheretogoapi.model.data.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String toToken(User user);

    Optional<String> getSubFromToken(String token);
}
