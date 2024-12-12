package com.nangman.delivery.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleDomainException(DomainException e) {
        log.error("Domain Exception: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException e) {
        log.error("Application Exception: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAspectException(AuthException e) {
        log.error("Aspect Exception: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<?> handleDefaultException(DefaultException e) {
        log.error("Default Exception: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
