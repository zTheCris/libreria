package com.example.libreria.dto;

public class NazionalitaDTO {
    private Integer id_nazionalita;
    private String nome;

    public NazionalitaDTO(Integer id_nazionalita, String nome) {
        this.id_nazionalita = id_nazionalita;
        this.nome = nome;
    }

    public NazionalitaDTO() {
    }

    @Override
    public String toString() {
        return "NazionalitaDTO{" +
                "id_nazionalita=" + id_nazionalita +
                ", nome='" + nome + '\'' +
                '}';
    }

    public Integer getId_nazionalita() {
        return id_nazionalita;
    }

    public void setId_nazionalita(Integer id_nazionalita) {
        this.id_nazionalita = id_nazionalita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
