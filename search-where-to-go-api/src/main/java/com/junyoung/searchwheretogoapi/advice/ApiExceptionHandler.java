package com.junyoung.searchwheretogoapi.advice;

import com.junyoung.searchwheretogoapi.exception.UserAlreadyCreatedException;
import com.junyoung.searchwheretogoapi.exception.UserAuthenticationException;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserAlreadyCreatedException.class)
  public ResponseEntity<ApiResponse<Object>> handleUserAlreadyCreatedException(UserAlreadyCreatedException throwable) {
    log.info(">> handleUserAlreadyCreatedException(message={})", throwable.getMessage());
    return ResponseEntity.ok(ApiResponse.fail(ResponseType.ALREADY_EXISTS_USER));
  }

  @ExceptionHandler(UserAuthenticationException.class)
  public ResponseEntity<ApiResponse<Object>> handleUserAuthenticationException(UserAuthenticationException throwable) {
    log.info(">> handleUserAuthenticationException(message={})", throwable.getMessage());
    return ResponseEntity.ok(ApiResponse.fail(ResponseType.NOT_LOGIN_USER));
  }

}
