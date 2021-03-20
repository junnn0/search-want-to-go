package com.junyoung.searchwheretogoapi.service.user;

import com.junyoung.searchwheretogoapi.exception.UserAlreadyCreatedException;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.model.UserParam;
import com.junyoung.searchwheretogoapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User createUser(UserParam userParam) {
    log.debug("> createUser(userParam={})", userParam);
    User user = userRepository.findByUsername(userParam.getUsername());
    if (user != null) {
      throw new UserAlreadyCreatedException(String.format("user %s is exists.", user.getUsername()));
    }
    return userRepository.save(new User(userParam.getUsername(), userParam.getPassword()));
  }

  public User findByUsername(String username) {
    log.debug("> findByUsername(username={})", username);
    return userRepository.findByUsername(username);
  }

}
