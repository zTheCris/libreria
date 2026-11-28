package com.example.libreria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AutoreRequest {
    @NotBlank(message = "Il nome dell'autore non può essere vuoto.")
    @Size(max =255, message =  "Il nome dell'autore non può avere più di 255 caratteri.")
    private String nome;
    @NotBlank(message = "Il cognome dell'autore non può essere vuoto.")
    @Size(max =255, message =  "Il cognome dell'autore non può avere più di 255 caratteri.")
    private String cognome;
    @NotBlank(message = "La nazionalita dell'autore non può essere vuota.")
    @Size(max =255, message =  "La nazionalita dell'autore  non può avere più di 255 caratteri.")
    private String nazionalita;
    @NotNull(message =  "La data non è stata inserita.")
    private LocalDate data;

    public AutoreRequest() {
    }

    public AutoreRequest(String nome, String cognome, String nazionalita, LocalDate data) {
        this.nome = nome;
        this.cognome = cognome;
        this.nazionalita = nazionalita;
        this.data = data;
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

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
