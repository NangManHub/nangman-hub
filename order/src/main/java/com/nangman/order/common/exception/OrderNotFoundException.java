package com.nangman.order.common.exception;

public class OrderNotFoundException extends DefaultException {
    public OrderNotFoundException() {
        super(ExceptionStatus.ORDER_NOT_FOUND);
    }
}
