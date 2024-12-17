package com.nangman.ai.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        logInfo(e);
        return createResponseEntity(e.getExceptionCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        logInfo(e);
        if (e.getCause() instanceof CustomException customException) {
            return createResponseEntity(customException.getExceptionCode());
        }
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionCode.COMMON_SERVER_ERROR.name(), e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(ExceptionCode exceptionCode) {
        return createResponseEntity(exceptionCode.getHttpStatus(), exceptionCode.name(), exceptionCode.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(HttpStatus httpStatus, String code, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(ExceptionResponse.builder()
                        .code(code)
                        .message(message)
                        .build()
                );
    }

    private void logInfo(Exception e) {
        log.info(e.getClass().getSimpleName(), e);
    }
}
