package com.junyoung.searchwheretogoapi.advice;

import com.junyoung.searchwheretogoapi.exception.UserAuthenticationException;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.common.ResponseType;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<Object> handleUserAuthenticationException(
            UserAuthenticationException ex) {
        log.info(">> handleUserAuthenticationException(message={})", ex.getMessage());
        return ResponseEntity.ok(ApiResponse.fail(ex.getResponseType()));
    }

    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(">> handleBindException(message={})", ex.getMessage());
        String errorMessage =
                ex.getFieldErrors().stream()
                        .map(
                                error ->
                                        String.format(
                                                "Request '%s' is invalid in value with '%s'",
                                                error.getField(), error.getRejectedValue()))
                        .collect(Collectors.joining());
        return ResponseEntity.ok(
                ApiResponse.fail(ResponseType.INVALID_PARAMETER.getCode(), errorMessage));
    }

    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.info(">> handleHttpMessageNotReadable(message={})", ex.getMessage(), ex);
        return ResponseEntity.ok(
                ApiResponse.fail(ResponseType.INVALID_PARAMETER.getCode(), ex.getMessage()));
    }

    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.info(">> handleMethodArgumentNotValid(message={})", ex.getMessage(), ex);
        return ResponseEntity.ok(
                ApiResponse.fail(ResponseType.INVALID_PARAMETER.getCode(), ex.getMessage()));
    }
}
