package com.example.exception;

public class InvalidAccountException extends RuntimeException { 
    public InvalidAccountException(String errorMessage) {
        super(errorMessage);
    }
}
