package com.nangman.delivery.common.exception;

public class ApplicationException extends DefaultException {
    public ApplicationException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
