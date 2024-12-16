package com.nangman.slack.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 내부 오류입니다."),

    API_CALL_FAILED(HttpStatus.BAD_REQUEST, "S-001", "Slack API 호출에 실패했습니다."),
    SLACK_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "S-002", "Slack 유저를 찾을 수 없습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "해당 Shipper를 찾을 수 없습니다."),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "H-001", "해당 허브를 찾을 수 없습니다.");

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
