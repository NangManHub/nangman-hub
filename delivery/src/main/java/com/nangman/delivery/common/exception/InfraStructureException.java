package com.nangman.delivery.common.exception;

public class InfraStructureException extends DefaultException {
    public InfraStructureException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
