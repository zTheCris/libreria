package com.example.libreria.dto;

import java.time.LocalDate;

public class LibroDTO {
    private Integer isbn;
    private String titolo;
    private LocalDate anno;
    private GenereDTO genere;

    public LibroDTO() {
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

    public LibroDTO(Integer isbn, String titolo, LocalDate anno, GenereDTO genere) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.anno = anno;
        this.genere = genere;
    }
}
