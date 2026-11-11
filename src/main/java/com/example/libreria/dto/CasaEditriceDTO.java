package com.example.libreria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CasaEditriceDTO {
    private Integer id_casaeditrice;

    @NotBlank(message = "Il nome della casa editrice non può essere vuoto.")
    @Size(max = 255 ,message = "Il nome della casa editrice non può superare 255 caratteri."  )
    private String nome;

    public CasaEditriceDTO() {
    }

    public CasaEditriceDTO(Integer id_casaeditrice, String nome) {
        this.id_casaeditrice = id_casaeditrice;
        this.nome = nome;
    }

    public Integer getId_casaeditrice() {
        return id_casaeditrice;
    }

    public void setId_casaeditrice(Integer id_casaeditrice) {
        this.id_casaeditrice = id_casaeditrice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "CasaEditriceDTO{" +
                "id_casaeditrice=" + id_casaeditrice +
                ", nome='" + nome + '\'' +
                '}';
    }
}
