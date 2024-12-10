package com.nangman.company.common.exception;

import org.springframework.http.HttpStatus;

public class CompanyNotFoundException extends DefaultException {
    public CompanyNotFoundException() {
        super(ExceptionStatus.COMPANY_NOT_FOUND);
    }
}
