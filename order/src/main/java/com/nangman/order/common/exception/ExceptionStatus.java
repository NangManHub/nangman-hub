package com.nangman.order.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    REQUEST_HEADER_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON_001", "요청 헤더가 없습니다."),
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "ORDER_001", "Company를 찾을 수 없습니다."),
    HUB_MANAGER_MISMATCHED(HttpStatus.BAD_REQUEST, "ORDER_002", "해당 허브의 담당자가 아닙니다."),
    COMPANY_AGENT_MISMATCHED(HttpStatus.FORBIDDEN, "ORDER_003", "해당 업체의 담당자가 아닙니다."),
    HUB_NOT_FOUND_BY_MANAGER_ID(HttpStatus.BAD_REQUEST, "ORDER_004", "해당 담당자는 담당하는 업체가 없습니다."),
    HUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "ORDER_005", "Hub를 찾을 수 없습니다."),
    MESSAGE_SERIALIZATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, "ORDER_006", "메시지 전송에 실패했습니다."),
    PRODUCT_QUANTITY_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "ORDER_007", "상품 수량이 부족합니다.");

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