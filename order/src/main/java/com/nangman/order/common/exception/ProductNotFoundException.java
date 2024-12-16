package com.nangman.order.common.exception;

public class ProductNotFoundException extends DefaultException {
    public ProductNotFoundException() {
        super(ExceptionStatus.PRODUCT_NOT_FOUND);
    }
}
