package com.nangman.order.common.exception;

public class DefaultException extends RuntimeException {
    private final ExceptionStatus exceptionStatus;
    public DefaultException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
    @Override
    public String getMessage() {
        return exceptionStatus.getMessage();
    }
    public int getStatus() {
        return exceptionStatus.getStatus();
    }
}