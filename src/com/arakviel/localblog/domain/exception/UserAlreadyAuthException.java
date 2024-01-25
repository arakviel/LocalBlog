package com.arakviel.localblog.domain.exception;

public class UserAlreadyAuthException extends RuntimeException {

    public UserAlreadyAuthException(String message) {
        super(message);
    }
}
