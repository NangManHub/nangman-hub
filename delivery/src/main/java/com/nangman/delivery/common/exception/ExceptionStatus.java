package com.nangman.delivery.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    //DELIVERY
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "d001", "해당 배송 정보를 찾을 수 없습니다."),

    //TRACK
    TRACK_SEQUENCE_DUPLICATED(HttpStatus.BAD_REQUEST, "t001", "중복된 배송 단계입니다."),
    TRACK_NOT_FOUND(HttpStatus.NOT_FOUND, "t002","해당 배송 경로를 찾을 수 없습니다."),
    TRACK_NOT_MOVING(HttpStatus.BAD_REQUEST, "t003", "배송중이 아닙니다."),
    TRACK_NOT_WAITING(HttpStatus.BAD_REQUEST, "t004", "배송 대기중이 아닙니다."),

    //SHIPPER
    SHIPPER_NOT_FOUND(HttpStatus.NOT_FOUND, "s001", "해당 배송원을 찾을 수 없습니다."),

    //AUTHORIZATION
    AUTHORIZATION_FAILED(HttpStatus.FORBIDDEN, "a001", "권한이 없습니다."),

    //HUB
    HUB_SERVER_ERROR(HttpStatus.NOT_FOUND, "h001", "허브정보를 받아올 수 없습니다. 다시 시도해주세요.");

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
