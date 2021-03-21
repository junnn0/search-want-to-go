package com.junyoung.searchwheretogoapi.model.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
    NOT_LOGIN_USER(1000, "user is not logged in."),
    NOT_EXISTS_USER(1001, "user is not exists."),

    ALREADY_EXISTS_USER(2000, "user is already exists."),
    INVALID_PARAMETER(2001, "parameter is invalid."),

    EXTERNAL_API_ERROR(3000, "external api error.");

    private final int code;
    private final String message;
}
