package com.example.libreria.model;

import jakarta.persistence.*;

@Entity
@Table(name="CasaEditrice")
public class CasaEditrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casaeditrice")
    private Integer id_casaeditrice;
    // Nullable =  false indica che il campo è NOT NULL.
    @Column(name="nome", nullable = false)
    private String nome;

    public CasaEditrice() {
    }
    public CasaEditrice(String nome) {
        this.nome = nome;
    }


    public CasaEditrice(Integer id_casaeditrice, String nome) {
        this.id_casaeditrice = id_casaeditrice;
        this.nome = nome;
    }

    public Integer getId_casaeditrice() {
        return id_casaeditrice;
    }

    public void setId_casaeditrice(Integer id_casaeditrice) {
        this.id_casaeditrice = id_casaeditrice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
