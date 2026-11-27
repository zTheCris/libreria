package com.example.libreria.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CasaEditriceRequest {
    @NotBlank(message = "Il nome della casa editrice non può essere vuoto")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CasaEditriceRequest(String nome) {
        this.nome = nome;
    }

    public CasaEditriceRequest() {
    }
}
