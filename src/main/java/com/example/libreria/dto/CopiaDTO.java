package com.example.libreria.dto;

public class CopiaDTO {
    private Integer idCopia;
    private LibroDTO libro;
    private CasaEditriceDTO casaEditrice;
    private Integer numeroCopie;

    public CopiaDTO() {
    }

    public CopiaDTO(Integer idCopia, LibroDTO libro, CasaEditriceDTO casaEditrice, Integer numeroCopie) {
        this.idCopia = idCopia;
        this.libro = libro;
        this.casaEditrice = casaEditrice;
        this.numeroCopie = numeroCopie;
    }

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public LibroDTO getLibro() {
        return libro;
    }

    public void setLibro(LibroDTO libro) {
        this.libro = libro;
    }

    public CasaEditriceDTO getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(CasaEditriceDTO casaEditrice) {
        this.casaEditrice = casaEditrice;
    }

    public Integer getNumeroCopie() {
        return numeroCopie;
    }

    public void setNumeroCopie(Integer numeroCopie) {
        this.numeroCopie = numeroCopie;
    }

}
