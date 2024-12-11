package com.nangman.user.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 내부 오류입니다."),
    DUPLICATE_USER_NAME(HttpStatus.BAD_REQUEST, "U-001", "중복된 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-002", "존재하지 않는 유저입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "U-003", "일치하지 않는 비밀번호 입니다."),
    WITHDRAWN_USER_ACCOUNT(HttpStatus.BAD_REQUEST, "U-004", "이미 탈퇴한 회원입니다.");

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