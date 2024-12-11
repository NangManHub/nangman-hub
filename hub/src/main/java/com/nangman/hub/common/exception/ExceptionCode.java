package com.nangman.hub.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    COMMON_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생하였습니다."),
    COMMON_INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터입니다."),

    ROLE_NOT_MASTER(HttpStatus.FORBIDDEN, "MASTER 권한이 필요합니다."),

    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
