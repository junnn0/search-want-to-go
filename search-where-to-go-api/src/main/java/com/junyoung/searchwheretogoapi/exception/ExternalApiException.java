package com.junyoung.searchwheretogoapi.exception;

import com.junyoung.searchwheretogoapi.model.common.ResponseType;

public class ExternalApiException extends RuntimeException {
    private final ResponseType responseType;

    public ExternalApiException(ResponseType responseType) {
        super(responseType.getMessage());
        this.responseType = responseType;
    }
}
