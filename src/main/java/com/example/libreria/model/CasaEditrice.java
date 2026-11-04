package com.example.libreria.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="CasaEditrice")
public class CasaEditrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casaeditrice")
    private Integer id_casaeditrice;
    // Nullable false indica che il campo è NOT NULL.
    @Column(name="nome", nullable = false)
    private String nome;
    @OneToMany(mappedBy = "casaEditrice")
    private Set<Copie> copie = new HashSet<>();

    public CasaEditrice() {
    }

    public CasaEditrice(Integer id_casaeditrice, String nome, Set<Copie> copie) {
        this.id_casaeditrice = id_casaeditrice;
        this.nome = nome;
        this.copie = copie;
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

    public Set<Copie> getCopie() {
        return copie;
    }

    public void setCopie(Set<Copie> copie) {
        this.copie = copie;
    }
}
