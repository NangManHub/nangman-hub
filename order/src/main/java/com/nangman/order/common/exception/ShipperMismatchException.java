package com.nangman.order.common.exception;

public class ShipperMismatchException extends DefaultException {
    public ShipperMismatchException() {
        super(ExceptionStatus.DELIVERY_SHIPPER_MISMATCHED);
    }
}
