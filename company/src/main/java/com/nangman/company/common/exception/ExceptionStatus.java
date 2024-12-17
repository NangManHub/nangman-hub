package com.nangman.company.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
    REQUEST_HEADER_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON_001", "요청 헤더가 없습니다."),
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMPANY_001", "Company를 찾을 수 없습니다."),
    HUB_MANAGER_MISMATCHED(HttpStatus.BAD_REQUEST, "COMPANY_002", "해당 허브의 담당자가 아닙니다."),
    COMPANY_AGENT_MISMATCHED(HttpStatus.FORBIDDEN, "COMPANY_003", "해당 업체의 담당자가 아닙니다."),
    HUB_NOT_FOUND_BY_MANAGER_ID(HttpStatus.BAD_REQUEST, "COMPANY_004", "해당 담당자는 담당하는 업체가 없습니다."),
    HUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMPANY_005", "Hub를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMPANY_006", "User를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT_001", "Product를 찾을 수 없습니다."),
    PRODUCT_QUANTITY_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "PRODUCT_002", "상품 수량이 부족합니다.");

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