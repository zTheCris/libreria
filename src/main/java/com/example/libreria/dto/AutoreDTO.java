package com.example.libreria.dto;

import java.time.LocalDate;

public class AutoreDTO {

    private Integer idAutore;
    private String nome;
    private String cognome;
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
