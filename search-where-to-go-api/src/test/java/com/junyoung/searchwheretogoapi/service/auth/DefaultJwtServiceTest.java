package com.junyoung.searchwheretogoapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.properties.JwtProperties;
import com.junyoung.searchwheretogoapi.util.TestDataUtil;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@EnableConfigurationProperties({JwtProperties.class})
@SpringBootTest
class DefaultJwtServiceTest {

  @Autowired private DefaultJwtService defaultJwtService;

  @Test
  void test_user_to_token() {
    assertNotNull(defaultJwtService.toToken(TestDataUtil.createUser()));
  }

  @Test
  void test_get_sub_from_token_success() {
    User user = TestDataUtil.createUser();
    String token = defaultJwtService.toToken(user);

    Optional<String> userIdOpt = defaultJwtService.getUserIdFromToken(token);
    assertTrue(userIdOpt.isPresent());
    assertEquals(user.getUserId(), userIdOpt.get());
  }
}
