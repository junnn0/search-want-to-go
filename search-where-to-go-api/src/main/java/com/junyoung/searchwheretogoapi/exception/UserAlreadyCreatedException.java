package com.junyoung.searchwheretogoapi.exception;

public class UserAlreadyCreatedException extends RuntimeException {
  public UserAlreadyCreatedException(String message) {
    super(message);
  }
}
