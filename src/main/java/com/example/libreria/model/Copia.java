package com.example.libreria.model;

import jakarta.persistence.*;

@Entity
@Table(name="Copie")
public class    Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCopia")
    private Integer idCopia;

    @ManyToOne
    @JoinColumn(name = "fk_isbn", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name="fk_casaeditrice", nullable = false)
    private CasaEditrice casaEditrice;
    @Column(name = "numero_copie", nullable = false)
    private Integer numeroCopie;

    public Copia(Integer numeroCopie, Libro libro, CasaEditrice casaEditrice) {
        this.numeroCopie = numeroCopie;
        this.libro = libro;
        this.casaEditrice = casaEditrice;
    }

    public Copia() {

    }

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public CasaEditrice getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(CasaEditrice casaEditrice) {
        this.casaEditrice = casaEditrice;
    }

    public Integer getNumeroCopie() {
        return numeroCopie;
    }

    public void setNumeroCopie(Integer numeroCopie) {
        this.numeroCopie = numeroCopie;
    }

    @Override
    public String toString() {
        return "Copie{" +
                "idCopia=" + idCopia +
                ", libro=" + libro +
                ", casaEditrice=" + casaEditrice +
                ", numeroCopie=" + numeroCopie +
                '}';
    }
}
