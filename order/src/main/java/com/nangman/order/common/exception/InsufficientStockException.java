package com.nangman.order.common.exception;

public class InsufficientStockException extends DefaultException {
    public InsufficientStockException() {
        super(ExceptionStatus.PRODUCT_QUANTITY_NOT_ENOUGH);
    }
}
