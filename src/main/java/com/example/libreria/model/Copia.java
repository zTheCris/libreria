package com.example.libreria.model;

import jakarta.persistence.*;



@Entity
@Table(name="Copie")
public class Copia {
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
    private Integer numero_copie;

    public Copia(Integer idCopia, Integer numero_copie, Libro libro, CasaEditrice casaEditrice) {
        this.idCopia = idCopia;
        this.numero_copie = numero_copie;
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

    public Integer getNumero_copie() {
        return numero_copie;
    }

    public void setNumero_copie(Integer numero_copie) {
        this.numero_copie = numero_copie;
    }

    @Override
    public String toString() {
        return "Copie{" +
                "idCopia=" + idCopia +
                ", libro=" + libro +
                ", casaEditrice=" + casaEditrice +
                ", numero_copie=" + numero_copie +
                '}';
    }
}
