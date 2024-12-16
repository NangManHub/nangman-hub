package com.nangman.order.common.exception;

public class CompanyNotFoundException extends DefaultException {
    public CompanyNotFoundException() {
        super(ExceptionStatus.COMPANY_NOT_FOUND);
    }
}
