package com.nangman.company.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMPANY_001", "Company를 찾을 수 없습니다."),
    HUB_NOT_MATCHED(HttpStatus.BAD_REQUEST, "COMPANY_002", "담당 Hub가 아닙니다.");

    private final int status;
    private final String customCode;
    private final String message;
    private final String err;

    ExceptionStatus(HttpStatus httpStatus, String customCode, String message) {
        this.status = httpStatus.value();
        this.customCode = customCode;
        this.message = message;
        this.err = httpStatus.getReasonPhrase();
    }
}