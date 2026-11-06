package com.example.libreria.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Autori")
public class Autori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAutore")
    private Integer idAutore;

    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "data")
    private LocalDate data;

    @ManyToMany
    @JoinTable(
            name = "Libri_Autori",
            joinColumns = @JoinColumn(name = "fk_autore"),
            inverseJoinColumns = @JoinColumn(name = "fk_isbn")
    )
    private Set<Libro> libri = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_nazionalita")
    private Nazionalita nazionalita;

    public Autori() {
    }

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

    public Nazionalita getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(Nazionalita nazionalita) {
        this.nazionalita = nazionalita;
    }

    public Autori(Integer idAutore, String nome, String cognome, LocalDate data, Set<Libro> libri, Nazionalita nazionalita) {
        this.idAutore = idAutore;
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.libri = libri;
        this.nazionalita = nazionalita;
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

    public Set<Libro> getLibri() {
        return libri;
    }

    public void setLibri(Set<Libro> libri) {
        this.libri = libri;
    }


}
