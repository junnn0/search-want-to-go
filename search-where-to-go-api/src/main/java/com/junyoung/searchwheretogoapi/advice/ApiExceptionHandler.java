package com.junyoung.searchwheretogoapi.advice;

import com.junyoung.searchwheretogoapi.exception.UserAuthenticationException;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserAuthenticationException.class)
    public ApiResponse<Object> handleUserAuthenticationException(UserAuthenticationException exception) {
        log.info(">> handleUserAuthenticationException(message={})", exception.getMessage());
        return ApiResponse.fail(exception.getResponseType());
    }

}
