package com.nangman.delivery.common.exception;

public class DomainException extends DefaultException {
    public DomainException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
