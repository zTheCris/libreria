package com.example.libreria.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Generi")
public class Genere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenere")
    private Integer idGenere;
    
    @Column(name = "nome")
    private String nome;

    public Integer getIdGenere() {
        return idGenere;
    }

    public void setIdGenere(Integer idGenere) {
        this.idGenere = idGenere;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genere() {
    }

    public Genere(Integer idGenere, String nome) {
        this.idGenere = idGenere;
        this.nome = nome;
    }
}


