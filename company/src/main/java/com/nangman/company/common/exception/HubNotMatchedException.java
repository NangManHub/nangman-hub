package com.nangman.company.common.exception;

public class HubNotMatchedException extends DefaultException {
    public HubNotMatchedException() {
        super(ExceptionStatus.HUB_MANAGER_MISMATCHED);
    }
}
