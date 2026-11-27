package com.example.libreria.dto;

/**
 * Serve per tornare al Client un JSON Stringa
 */
public class StringDTO {
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
