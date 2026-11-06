package com.example.libreria.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Libri")
public class Libro {
    @Id
    @Column(name = "isbn")
    private Integer isbn;
    @Column(name = "titolo")
    private String titolo;
    @Column(name="anno")
    private LocalDate anno;
    @Column(name="prezzo")
    private Integer prezzo;

    /*
    Una foreign key dei generi spunta piu' volte nei record in Libri.
    Per le tabelle con foreign key, la relazione può essere ManyToMany o ManyToOne.
     */
    @ManyToOne
    @JoinColumn(name = "fk_genere")
    private Genere genere;

    @ManyToMany
    @JoinTable(
            name="Libri_Autori",
            joinColumns = @JoinColumn(name = "fk_isbn"),
            inverseJoinColumns = @JoinColumn(name="fk_autore")
    )
    private Set<Autori> autori;

    @OneToMany(mappedBy = "libro")
    private Set<Copie> copie = new HashSet<>();



    public Libro() {
    }

    public Libro(Integer isbn, String titolo, LocalDate anno, Integer prezzo, Genere genere) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.anno = anno;
        this.prezzo = prezzo;
        this.genere = genere;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public LocalDate getAnno() {
        return anno;
    }

    public void setAnno(LocalDate anno) {
        this.anno = anno;
    }

    public Integer getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }
}
