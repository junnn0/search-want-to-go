package com.junyoung.searchwheretogoapi.exception;

import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {
    private final ResponseType responseType = ResponseType.EXTERNAL_API_ERROR;

    public ExternalApiException(Throwable throwable) {
        super(ResponseType.EXTERNAL_API_ERROR.getMessage(), throwable);
    }
}
