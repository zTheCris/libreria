package com.example.libreria.exception;

public class LibroNotFoundException extends RuntimeException {

    public LibroNotFoundException(String message) {
        super(message);
    }
    public LibroNotFoundException(){
        super("Libro non trovato.");
    }
}
