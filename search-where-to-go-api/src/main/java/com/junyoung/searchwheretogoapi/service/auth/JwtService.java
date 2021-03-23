package com.junyoung.searchwheretogoapi.service.auth;

import com.junyoung.searchwheretogoapi.model.data.User;
import java.util.Optional;

public interface JwtService {
  String toToken(User user);

  Optional<String> getUserIdFromToken(String token);
}
