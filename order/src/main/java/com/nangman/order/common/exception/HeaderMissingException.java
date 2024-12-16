package com.nangman.order.common.exception;

public class HeaderMissingException extends DefaultException {
    public HeaderMissingException() {
        super(ExceptionStatus.REQUEST_HEADER_NOT_FOUND);
    }
}
