package com.example.libreria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CopiaRequest {
    @NotNull(message ="L'ISBN non può essere vuoto")
    @Positive(message = "L'ISBN non può essere minore di 0.")
    private Integer isbn;

    @NotNull(message ="Il numero delle copie non può essere vuoto.")
    @PositiveOrZero(message = "Il numero delle copie non può essere minore di 0.")
    private Integer numeroCopie;

    @NotBlank(message =  "La casa editrice non può essere vuota.")
    private String casaEditrice;

    public CopiaRequest(Integer isbn, String casaEditrice, Integer numeroCopie) {
        this.isbn = isbn;
        this.casaEditrice = casaEditrice;
        this.numeroCopie = numeroCopie;
    }

    public CopiaRequest() {
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Integer getNumeroCopie() {
        return numeroCopie;
    }

    public void setNumeroCopie(Integer numeroCopie) {
        this.numeroCopie = numeroCopie;
    }

    public String getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(String casaEditrice) {
        this.casaEditrice = casaEditrice;
    }
    // Per debug
    @Override
    public String toString() {
        return "CopiaRequest{" +
                "isbn=" + isbn +
                ", numeroCopie=" + numeroCopie +
                ", casaEditrice='" + casaEditrice + '\'' +
                '}';
    }
}
