package com.example.libreria.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Nazionalita")
public class Nazionalita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nazionalita")
    private Integer idNazionalita;
    @Column(name = "nome")
    private String nome;

    public Nazionalita() {
    }

    public Nazionalita(Integer idNazionalita, String nome) {
        this.idNazionalita = idNazionalita;
        this.nome = nome;

    }
    public Nazionalita(String nome) {
        this.nome = nome;

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

