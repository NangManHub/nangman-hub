package com.nangman.user.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 내부 오류입니다."),
    FEIGN_CLIENT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "F-001", "Feign Client 서버 내부 오류입니다."),

    DUPLICATE_USER_NAME(HttpStatus.BAD_REQUEST, "U-001", "중복된 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-002", "존재하지 않는 유저입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "U-003", "일치하지 않는 비밀번호 입니다."),
    WITHDRAWN_USER_ACCOUNT(HttpStatus.BAD_REQUEST, "U-004", "이미 탈퇴한 회원입니다."),
    MASTER_ROLE_REQUIRED(HttpStatus.FORBIDDEN, "U-005", "마스터 권한이 필요합니다."),
    MASTER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U-006", "요청하신 마스터 권한 유저를 찾을 수 없습니다."),
    USER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "U-007", "해당 자원에 대한 권한이 없습니다."),

    ONLY_SHIPPER_REGISTERED(HttpStatus.BAD_REQUEST, "S-001", "SHIPPER로 등록된 사용자만 허용됩니다."),
    SHIPPER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "S-002", "해당 요청의 권한이 없습니다."),
    SHIPPER_NOT_FOUND(HttpStatus.NOT_FOUND, "S-003", "해당하는 Shipper가 없습니다."),

    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "H-001", "허브가 존재하지 않습니다.");

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
