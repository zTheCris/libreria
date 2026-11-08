package com.example.libreria.dto;

public class NazionalitaDTO {
    private Integer idNazionalita;
    private String nome;

    public NazionalitaDTO(Integer idNazionalita, String nome) {
        this.idNazionalita = idNazionalita;
        this.nome = nome;
    }

    public NazionalitaDTO() {
    }



    public Integer getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(Integer idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
