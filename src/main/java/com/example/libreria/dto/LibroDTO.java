package com.example.libreria.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LibroDTO {
    private Integer isbn;
    private String titolo;
    private LocalDate anno;
    private GenereDTO genere;
    private Integer prezzo;

    public Integer getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }

    public void setAutori(Set<AutoreDTO> autori) {
        this.autori = autori;
    }

    /**
    Quando voglio fare una getAll dei libri, ho bisogno di sapere quali autori hanno scritto ogni libro.
    Dato che uno dei motivi per cui abbiamo creato il DTO è evitare la ricorsione infinita,
    facciamo che SOLO i Libri possono sapere da chi sono stati scritti.
    Per avere una relazione bilaterale, dovremmo usare il mappedBy a livello di Entity.
     */
    private Set<AutoreDTO> autori = new HashSet<>();

    public LibroDTO() {
    }

    public LibroDTO(Integer isbn, String titolo, LocalDate anno, GenereDTO genere, Integer prezzo, Set<AutoreDTO> autori) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.anno = anno;
        this.genere = genere;
        this.prezzo = prezzo;
        this.autori = autori;
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

    public GenereDTO getGenere() {
        return genere;
    }

    public void setGenere(GenereDTO genere) {
        this.genere = genere;
    }

    public Set<AutoreDTO> getAutori() {
        return autori;
    }

    public void setAutoriDTO(Set<AutoreDTO> autori) {
        this.autori = autori;
    }
}
