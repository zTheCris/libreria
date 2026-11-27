package com.example.libreria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LibroRequest {
    @NotNull(message = "L'ISBN non è stato inserito. Riprovare")
    @Positive(message = "L'ISBN non può essere minore di 0.")
    private Integer isbn;
    
    @NotNull(message = "Il prezzo non è stato inserito.")
    @Positive(message = "Il prezzo non può essere minore di 0.")
    private Integer prezzo;

    @NotBlank(message = "Il titolo non è stato inserito.")
    private String titolo;

    @NotBlank(message = "Il genere non è stato inserito.")
    private String genere;

    @NotNull(message = "La data non è stata inserita.")
    private LocalDate data;

    @NotEmpty(message = "Gli ID degli autori devono essere presenti.")
    @NotNull(message = "Gli ID degli autori non sono stati inseriti. ")
    private Set<Integer> autoriIDs = new HashSet<>();

    public LibroRequest(Integer isbn, Integer prezzo, String titolo, String genere, LocalDate data, Set<Integer> autoriIDs) {
        this.isbn = isbn;
        this.prezzo = prezzo;
        this.titolo = titolo;
        this.genere = genere;
        this.data = data;
        this.autoriIDs = autoriIDs;
    }

    public LibroRequest() {
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Integer getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Set<Integer> getAutoriIDs() {
        return autoriIDs;
    }

    public void setAutoriIDs(Set<Integer> autoriIDs) {
        this.autoriIDs = autoriIDs;
    }
}
