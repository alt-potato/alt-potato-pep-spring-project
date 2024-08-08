package com.example.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
