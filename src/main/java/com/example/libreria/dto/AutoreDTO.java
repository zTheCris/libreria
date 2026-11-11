package com.example.libreria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AutoreDTO {
    // Uso i validation sui singoli campi

    private Integer idAutore;

    @NotBlank(message = "Il nome dell'autore non può essere vuoto.")
    @Size(max = 255 ,message = "Il nome dell'autore non può superare 255 caratteri."  )
    private String nome;
    @NotBlank(message = "Il cognome dell'autore non può essere vuoto.")
    @Size(max = 255 ,message = "Il nome dell'autore non può superare 255 caratteri."  )
    private String cognome;
    @NotNull(message = "La data non può essere vuota.")
    private LocalDate data;

    private NazionalitaDTO nazionalita;


    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public NazionalitaDTO getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(NazionalitaDTO nazionalita) {
        this.nazionalita = nazionalita;
    }

    public AutoreDTO() {
    }

    public AutoreDTO(Integer idAutore, String nome,String cognome, LocalDate data) {
        this.idAutore = idAutore;
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
    }

    public Integer getIdAutore() {
        return idAutore;
    }

    public void setIdAutore(Integer idAutore) {
        this.idAutore = idAutore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
