package com.nangman.order.common.exception;

public class HubNotMatchedException extends DefaultException {
    public HubNotMatchedException() {
        super(ExceptionStatus.HUB_MANAGER_MISMATCHED);
    }
}
