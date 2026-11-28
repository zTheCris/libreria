package com.example.libreria.exception;

public class UtenteNotFoundException extends RuntimeException {
    public UtenteNotFoundException(String message) {
        super(message);
    }
}
