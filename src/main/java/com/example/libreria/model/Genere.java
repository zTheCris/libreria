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
    // Mapped by deve specificare il campo all'interno della classe relazionata.
    // In questo caso, Libri ha tra i vari attributi "Genere".
    @OneToMany(mappedBy = "genere")
    private Set<Libro> libri = new HashSet<>();

    public Genere() {
    }

    public Genere(Integer idGenere, String nome, Set<Libro> libri) {
        this.idGenere = idGenere;
        this.nome = nome;
        this.libri = libri;
    }

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

    public Set<Libro> getLibri() {
        return libri;
    }

    public void setLibri(Set<Libro> libri) {
        this.libri = libri;
    }
}


