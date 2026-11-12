package com.example.libreria.exception;

import com.example.libreria.model.Autore;
import com.example.libreria.model.Copia;
import com.example.libreria.model.Genere;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Il metodo serve per costruire la risposta associando l'errore al messaggio stringa
     * @return
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus statoHTTP, String messaggio){
        Map<String,Object> corpoRet = new HashMap<>();
        corpoRet.put("stato",statoHTTP.value());
        corpoRet.put("errore",statoHTTP.getReasonPhrase());
        corpoRet.put("messaggio",messaggio);

        return ResponseEntity.status(statoHTTP).body(corpoRet);

    }
    @ExceptionHandler(LibroNotFoundException.class)
    public ResponseEntity<Map<String,Object>> LibroNotFound(LibroNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(AutoreNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> AutoreNotFound(AutoreNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(GenereNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> GenereNotFound(GenereNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(CopiaNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> CopiaNotFound(CopiaNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(CasaEditriceNotFound.class)
    public ResponseEntity<Map<String,Object>> CasaEditriceNotFound(CasaEditriceNotFound e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(NazionalitaNotFoundException.class)
    public ResponseEntity<Map<String,Object>> NazionalitaNotFound(NazionalitaNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    // Status code per entià già esistenti
    @ExceptionHandler(LibroAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> LibroAlreayExists(LibroAlreadyExistsException e){
        return buildResponse(HttpStatus.CONFLICT,e.getMessage());
    }
}
