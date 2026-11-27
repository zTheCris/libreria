package com.example.libreria.dto;

public class UtenteDTO {
    private Integer idUtenete;
    private String email;
    private String nome;
    private String cognome;
    // La password NON la faccio vedere

    public Integer getIdUtenete() {
        return idUtenete;
    }

    public void setIdUtenete(Integer idUtenete) {
        this.idUtenete = idUtenete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public UtenteDTO(Integer idUtenete, String email, String nome, String cognome) {
        this.idUtenete = idUtenete;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }
}
