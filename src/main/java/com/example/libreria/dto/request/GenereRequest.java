package com.example.libreria.dto.request;

public class GenereRequest {

        private String nome;

    public GenereRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
