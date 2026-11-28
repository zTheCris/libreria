package com.example.libreria.exception;

public class LibroAlreadyExistsException extends RuntimeException {
    public LibroAlreadyExistsException(String message){
        super(message);
    }
}
