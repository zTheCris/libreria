package com.example.libreria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    // Gestisco anche le Validation tramite l'eccezione MethodArgumentNotValidException

    /**
     * Gestisce gli errori generati quando un DTO non supera la validazione (usando Validation).
     *  <br>
     * Tramite BindingResult recuperiamo tutti gli errori riscontrati.
     * <br>
     * Con getDefaultMessage() estraiamo i messaggi personalizzati
     * definiti nelle annotazioni come @NotBlank, @NotNull ecc.
     *
     * @param e eccezione contenente gli errori di validazione
     * @return risposta HTTP 400 (Bad Request) con il messaggio di errore
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> ValidazioneArgomenti(MethodArgumentNotValidException e){
        // Il messaggio di errore contiene una stringa lunghissima. Le validation
        return buildResponse(HttpStatus.BAD_REQUEST,e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; ")));
    }
    // Invece per i tipi di conversione, posso usare il
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String,Object>> ValidazioneTipi(MethodArgumentTypeMismatchException e){
        return buildResponse(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    // Gestore per la formattazione JSON/se la richiesta è stata fatta male
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,Object>> ValidazioneHTTP(HttpMessageNotReadableException e){
        return buildResponse(HttpStatus.BAD_REQUEST, "Formattazione JSON errata. Assicurati di aver inserito tutti i campi correttamente.");
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
    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<Map<String,Object>> utenteNotFound(UtenteNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    // Handler per entià già esistenti
    @ExceptionHandler(LibroAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> LibroAlreayExists(LibroAlreadyExistsException e){
        return buildResponse(HttpStatus.CONFLICT,e.getMessage());
    }
    @ExceptionHandler(UtenteAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> utenteAlreadyExists(UtenteAlreadyExistsException e){
        return buildResponse(HttpStatus.CONFLICT,e.getMessage());
    }
    @ExceptionHandler(CasaEditriceAlreadyExists.class)
    public ResponseEntity<Map<String,Object>> casaEditriceAlreadyExists(CasaEditriceAlreadyExists e){
        return buildResponse(HttpStatus.CONFLICT, e.getMessage());
    }

}
