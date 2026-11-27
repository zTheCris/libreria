package com.example.libreria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CasaEditriceRequest {
    @NotNull(message = "Il nome della casa editrice non può essere vuoto")
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
}
