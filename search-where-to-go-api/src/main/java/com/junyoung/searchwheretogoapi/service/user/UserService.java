package com.junyoung.searchwheretogoapi.service.user;

import com.junyoung.searchwheretogoapi.exception.UserAlreadyCreatedException;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.model.UserParam;
import com.junyoung.searchwheretogoapi.repository.UserRepository;
import com.junyoung.searchwheretogoapi.service.auth.EncryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final EncryptService encryptService;
  private final UserRepository userRepository;

  public User createUser(UserParam userParam) {
    log.debug("> createUser(userParam={})", userParam);
    User foundUser = userRepository.findByUsername(userParam.getUsername());
    if (foundUser != null) {
      throw new UserAlreadyCreatedException(String.format("user %s is exists.", foundUser.getUsername()));
    }
    User user = new User(userParam.getUsername(), encryptService.encryptPassword(userParam.getPassword()));
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
