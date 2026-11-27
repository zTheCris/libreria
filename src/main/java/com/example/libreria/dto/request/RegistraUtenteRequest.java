package com.example.libreria.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegistraUtenteRequest {
    @Email(message = "Lo username deve essere un'email valida. Riprovare.")
    @NotBlank(message =  "L'email non può essere vuota")
    private String email;
    @NotBlank(message =  "Il nome non può essere vuoto")
    private String nome;
    @NotBlank(message =  "Il cognome non può essere vuoto")
    private String cognome;
    @NotBlank(message =  "La password non può essere vuota")
    private String password;

    public RegistraUtenteRequest() {
    }

    public RegistraUtenteRequest(String email, String nome, String cognome, String password) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
