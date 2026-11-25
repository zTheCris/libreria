package com.example.libreria.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Serve per tornare al Client un JSON Stringa
 */
public class StringDTO {
    @NotBlank(message = "Il risultato non può essere vuoto.")
    private String result;

    public StringDTO(String result) {
        this.result = result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
