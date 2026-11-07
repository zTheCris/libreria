package com.example.libreria.dto;

public class CopiaDTO {
    private Integer idCopia;
    private LibroDTO libroDTO;
    private CasaEditriceDTO casaEditriceDTO;
    private Integer numero_copie;

    public CopiaDTO() {
    }

    public CopiaDTO(Integer idCopia, LibroDTO libroDTO, CasaEditriceDTO casaEditriceDTO, Integer numero_copie) {
        this.idCopia = idCopia;
        this.libroDTO = libroDTO;
        this.casaEditriceDTO = casaEditriceDTO;
        this.numero_copie = numero_copie;
    }

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public LibroDTO getLibroDTO() {
        return libroDTO;
    }

    public void setLibroDTO(LibroDTO libroDTO) {
        this.libroDTO = libroDTO;
    }

    public CasaEditriceDTO getCasaEditriceDTO() {
        return casaEditriceDTO;
    }

    public void setCasaEditriceDTO(CasaEditriceDTO casaEditriceDTO) {
        this.casaEditriceDTO = casaEditriceDTO;
    }

    public Integer getNumero_copie() {
        return numero_copie;
    }

    public void setNumero_copie(Integer numero_copie) {
        this.numero_copie = numero_copie;
    }

    @Override
    public String toString() {
        return "CopieDTO{" +
                "idCopia=" + idCopia +
                ", libroDTO=" + libroDTO +
                ", casaEditriceDTO=" + casaEditriceDTO +
                ", numero_copie=" + numero_copie +
                '}';
    }
}
