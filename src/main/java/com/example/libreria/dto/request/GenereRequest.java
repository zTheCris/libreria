package com.example.libreria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GenereRequest {
    @NotBlank(message = "Il nome del genere non può essere vuoto.")
    @NotNull(message = "Non hai inserito il nome.")
    @Size(max = 45, message = "Il nome del genere non può avere piu' di 45 caratteri.")
    private String nome;

    public GenereRequest(String nome) {
        this.nome = nome;
    }

    public GenereRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
