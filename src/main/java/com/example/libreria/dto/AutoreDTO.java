package com.example.libreria.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AutoreDTO {

    private Integer idAutore;
    private String nome;
    private String cognome;
    private LocalDate data;
    private NazionalitaDTO nazionalitaDTO;


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

    public NazionalitaDTO getNazionalitaDTO() {
        return nazionalitaDTO;
    }

    public void setNazionalitaDTO(NazionalitaDTO nazionalitaDTO) {
        this.nazionalitaDTO = nazionalitaDTO;
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

    public LocalDate getAnno() {
        return data;
    }

    public void setAnno(LocalDate data) {
        this.data = data;
    }

}
