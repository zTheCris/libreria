package com.example.libreria.exception;

public class UtenteAlreadyExistsException extends RuntimeException {
    public UtenteAlreadyExistsException(String message) {
        super(message);
    }
}
