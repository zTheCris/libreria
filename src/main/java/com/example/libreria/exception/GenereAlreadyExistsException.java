package com.example.libreria.exception;

public class GenereAlreadyExistsException extends RuntimeException {
    public GenereAlreadyExistsException(String message) {
        super(message);
    }
}
