package com.example.libreria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenereDTO {
    private Integer idGenere;

    @NotBlank(message = "Il nome del genere non può essere vuoto.")
    @Size(max = 255 ,message = "Il nome del genere non può superare 255 caratteri."  )
    private String nome;

    public GenereDTO() {
    }

    public Integer getIdGenere() {
        return idGenere;
    }

    public void setIdGenere(Integer idGenere) {
        this.idGenere = idGenere;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GenereDTO(Integer idGenere, String nome) {
        this.idGenere = idGenere;
        this.nome = nome;
    }
}
