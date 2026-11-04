package com.example.libreria.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Nazionalita")
public class Nazionalita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_nazionalita")
    private Integer id_nazionalita;
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "nazionalita")
    private Set<Autori> autori = new HashSet<>();

    public Nazionalita() {
    }

    public Nazionalita(Integer id_nazionalita, String nome, Set<Autori> autori) {
        this.id_nazionalita = id_nazionalita;
        this.nome = nome;
        this.autori = autori;
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

    public Set<Autori> getAutori() {
        return autori;
    }

    public void setAutori(Set<Autori> autori) {
        this.autori = autori;
    }
}