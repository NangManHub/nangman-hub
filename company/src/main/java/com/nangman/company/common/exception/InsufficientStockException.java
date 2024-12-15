package com.nangman.company.common.exception;

public class InsufficientStockException extends DefaultException {
    public InsufficientStockException() {
        super(ExceptionStatus.PRODUCT_QUANTITY_NOT_ENOUGH);
    }
}
