package com.nangman.company.common.exception;

public class HeaderMissingException extends DefaultException {
    public HeaderMissingException() {
        super(ExceptionStatus.REQUEST_HEADER_NOT_FOUND);
    }
}
