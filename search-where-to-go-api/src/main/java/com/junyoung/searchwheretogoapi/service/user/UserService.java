package com.junyoung.searchwheretogoapi.service.user;

import com.junyoung.searchwheretogoapi.exception.UserAuthenticationException;
import com.junyoung.searchwheretogoapi.model.api.UserParam;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.repository.UserRepository;
import com.junyoung.searchwheretogoapi.service.auth.EncryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final EncryptService encryptService;
  private final UserRepository userRepository;

  @Transactional
  public User createUser(UserParam userParam) {
    log.debug("> createUser(userParam={})", userParam);
    User foundUser = userRepository.findByUsername(userParam.getUsername());
    if (foundUser != null) {
      throw new UserAuthenticationException(ResponseType.ALREADY_EXISTS_USER);
    }
    User user =
        new User(userParam.getUsername(), encryptService.encryptPassword(userParam.getPassword()));
    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    log.debug("> findByUsername(username={})", username);
    return userRepository.findByUsername(username);
  }

  public boolean isCorrectPassword(String requestedPassword, User user) {
    return encryptService.checkPassword(user.getPassword(), requestedPassword);
  }
}
