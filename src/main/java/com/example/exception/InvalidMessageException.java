package com.example.exception;

public class InvalidMessageException extends RuntimeException { 
    public InvalidMessageException(String errorMessage) {
        super(errorMessage);
    }
}
