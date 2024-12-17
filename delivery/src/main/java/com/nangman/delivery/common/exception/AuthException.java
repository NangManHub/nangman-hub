package com.nangman.delivery.common.exception;

public class AuthException extends DefaultException {
    public AuthException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
