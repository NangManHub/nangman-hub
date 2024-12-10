package com.nangman.user.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 내부 오류입니다."),
    DUPLICATE_USER_NAME(HttpStatus.BAD_REQUEST, "U-001", "중복된 아이디 입니다.");

    // 상태, 에러 코드, 메시지
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    // 생성자
    ExceptionType(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
