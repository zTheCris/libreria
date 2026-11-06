package com.example.libreria.dto;

import com.example.libreria.model.Nazionalita;

import java.time.LocalDate;

public class AutoreDTO {

    private Integer idAutore;
    private String nome;
    private LocalDate anno;
    private NazionalitaDTO nazionalitaDTO;
    public AutoreDTO() {
    }

    public AutoreDTO(Integer idAutore, String nome, LocalDate anno) {
        this.idAutore = idAutore;
        this.nome = nome;
        this.anno = anno;
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
        return anno;
    }

    public void setAnno(LocalDate anno) {
        this.anno = anno;
    }
}
