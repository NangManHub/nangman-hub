package com.nangman.order.common.exception;

public class MessageSerializationException extends DefaultException {
    public MessageSerializationException() {
        super(ExceptionStatus.MESSAGE_SERIALIZATION_FAILED);
    }
}
