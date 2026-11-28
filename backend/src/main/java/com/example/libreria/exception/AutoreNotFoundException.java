package com.example.libreria.exception;

public class AutoreNotFoundException extends RuntimeException {
    public AutoreNotFoundException(String message) {
        super(message);
    }
}
