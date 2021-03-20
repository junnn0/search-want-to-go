package com.junyoung.searchwheretogoapi.model.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
  NOT_LOGIN_USER(1000, "user is not logged in."),

  ALREADY_EXISTS_USER(2000, "user is already exists.");

  private final int code;
  private final String message;
}
