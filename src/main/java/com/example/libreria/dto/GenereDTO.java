package com.example.libreria.dto;

public class GenereDTO {
    private Integer idGenere;
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
