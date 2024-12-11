package com.nangman.delivery.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    //DELIVERY
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "d001", "해당 배송 정보를 찾을 수 없습니다."),

    //TRACK
    TRACK_SEQUENCE_DUPLICATED(HttpStatus.BAD_REQUEST, "t001", "중복된 배송 단계입니다."),
    TRACK_NOT_FOUND(HttpStatus.NOT_FOUND, "t002","해당 배송 경로를 찾을 수 없습니다.");

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
