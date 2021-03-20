package com.junyoung.searchwheretogoapi.exception;

import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import lombok.Getter;

@Getter
public class UserAuthenticationException extends RuntimeException {
    private final ResponseType responseType;

    public UserAuthenticationException(ResponseType responseType) {
        super(responseType.getMessage());
        this.responseType = responseType;
    }
}
