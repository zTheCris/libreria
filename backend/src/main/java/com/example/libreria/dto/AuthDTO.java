package com.example.libreria.dto;

/**
 * In risposta dopo essersi autenticato, il Server manderà al client il {@code token} effettivo per l'autenticazione tramite JWT.
 */
public class AuthDTO {

    private String token;

    public AuthDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
