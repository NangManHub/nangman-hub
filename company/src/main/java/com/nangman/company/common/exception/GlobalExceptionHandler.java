package com.nangman.company.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<?> handleDefaultException(DefaultException e) {
        log.error("handleDefaultException: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}