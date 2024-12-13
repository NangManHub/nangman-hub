package com.nangman.company.common.exception;

public class ProductNotFoundException extends DefaultException {
    public ProductNotFoundException() {
        super(ExceptionStatus.PRODUCT_NOT_FOUND);
    }
}
