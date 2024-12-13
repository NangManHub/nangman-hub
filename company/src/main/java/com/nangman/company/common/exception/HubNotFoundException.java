package com.nangman.company.common.exception;

public class HubNotFoundException extends DefaultException {
    public HubNotFoundException() {
        super(ExceptionStatus.HUB_NOT_FOUND);
    }
}
