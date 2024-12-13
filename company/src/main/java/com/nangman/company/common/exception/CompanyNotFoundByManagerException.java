package com.nangman.company.common.exception;

public class CompanyNotFoundByManagerException extends DefaultException {
    public CompanyNotFoundByManagerException() {
        super(ExceptionStatus.HUB_NOT_FOUND_BY_MANAGER_ID);
    }
}
