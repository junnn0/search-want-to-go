package com.junyoung.searchwheretogoapi.controller;

import javax.validation.Valid;

import com.junyoung.searchwheretogoapi.exception.UserAuthenticationException;
import com.junyoung.searchwheretogoapi.model.UserParam;
import com.junyoung.searchwheretogoapi.model.UserWithToken;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.service.auth.JwtService;
import com.junyoung.searchwheretogoapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;

  @GetMapping("/user")
  public ResponseEntity<ApiResponse<UserWithToken>> getUser(@AuthenticationPrincipal User user) {
    User currentUser = userService.findByUsername(user.getUsername());
    return ResponseEntity.ok(ApiResponse.success(new UserWithToken(currentUser, jwtService.toToken(currentUser))));
  }

  @PostMapping("/users")
  public ResponseEntity<ApiResponse<UserWithToken>> createUser(@Valid @RequestBody UserParam userParam) {
    User createdUser = userService.createUser(userParam);
    return ResponseEntity.ok(ApiResponse.success(new UserWithToken(createdUser, jwtService.toToken(createdUser))));
  }

  @PostMapping("/users/login")
  public ResponseEntity<ApiResponse<UserWithToken>> loginUser(@Valid @RequestBody UserParam userParam) {
    User user = userService.findByUsername(userParam.getUsername());
    if (user != null && userService.isCorrectPassword(userParam.getPassword(), user)) {
      return ResponseEntity.ok(ApiResponse.success(new UserWithToken(user, jwtService.toToken(user))));
    } else {
      throw new UserAuthenticationException(String.format("user '%s' is not exists.", userParam.getUsername()));
    }
  }

}
