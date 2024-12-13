package com.nangman.company.common.exception;

public class UserNotFoundException extends DefaultException {
    public UserNotFoundException() {
        super(ExceptionStatus.USER_NOT_FOUND);
    }
}
