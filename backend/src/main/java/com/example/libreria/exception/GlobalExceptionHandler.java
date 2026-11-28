package com.example.libreria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
     * Il metodo serve per costruire la risposta JSON associando lo stato HTTP al messaggio dell'eccezione.
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
     * Gestisce gli errori generati quando un DTO non supera la validazione (usando {@code Validation}).
     *  <br>
     * Tramite {@code BindingResult} della libreria {@code Bind} recuperiamo tutti gli errori riscontrati.
     * <br>
     * Con getDefaultMessage() estraiamo i messaggi personalizzati
     * definiti nelle annotazioni come @NotBlank, @NotNull ecc.
     *
     * @param e eccezione contenente gli errori di validazione
     * @return risposta HTTP 400 (Bad Request) con il messaggio di errore
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> validazioneArgomenti(MethodArgumentNotValidException e){
        // Il messaggio di errore contiene una stringa lunghissima. Le validation
        return buildResponse(HttpStatus.BAD_REQUEST,e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; ")));
    }
    // Invece per i tipi di conversione, posso usare il
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String,Object>> validazioneTipi(MethodArgumentTypeMismatchException e){
        return buildResponse(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    // Gestore per la formattazione JSON/se la richiesta è stata fatta male
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,Object>> validazioneHTTP(HttpMessageNotReadableException e){
        return buildResponse(HttpStatus.BAD_REQUEST, "Formattazione JSON errata. Assicurati di aver inserito tutti i campi correttamente.");
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String,Object>> gestisciNullPointerException(NullPointerException e){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Errore interno al server. Contattate amministratore.");
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String,Object>> gestisciMetodoNonSupportato(HttpRequestMethodNotSupportedException e){
        return buildResponse(HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
    }

    /*
    =============================================================
                        ECCEZIONI PERSONALIZZATE
    =============================================================
     */
    @ExceptionHandler(LibroNotFoundException.class)
    public ResponseEntity<Map<String,Object>> libroNotFound(LibroNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(AutoreNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> autoreNotFound(AutoreNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(GenereNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> genereNotFound(GenereNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(CopiaNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> copiaNotFound(CopiaNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(CasaEditriceNotFound.class)
    public ResponseEntity<Map<String,Object>> casaEditriceNotFound(CasaEditriceNotFound e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(NazionalitaNotFoundException.class)
    public ResponseEntity<Map<String,Object>> nazionalitaNotFound(NazionalitaNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<Map<String,Object>> utenteNotFound(UtenteNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    // Handler per entià già esistenti
    @ExceptionHandler(LibroAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> libroAlreayExists(LibroAlreadyExistsException e){
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
