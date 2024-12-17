package com.nangman.ai.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String code;
    private final String message;

    @Builder
    private ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .code(exceptionCode.name())
                .message(exceptionCode.getMessage())
                .build();
    }
}
