package com.nangman.user.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<RestApiException> handleException(CustomException e) {
        return ResponseEntity.status(e.getExceptionType().getHttpStatus()).body(new RestApiException(e.getExceptionType().getMessage(), e.getExceptionType().getHttpStatus().value()));
    }
}
