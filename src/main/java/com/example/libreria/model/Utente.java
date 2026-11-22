package com.example.libreria.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name= "Utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUtente")
    private Integer idUtente;

    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "password",nullable = false)
    private String password;

    public Utente(String email, String password, String cognome, String nome, Integer idUtente) {
        this.email = email;
        this.password = password;
        this.cognome = cognome;
        this.nome = nome;
        this.idUtente = idUtente;
    }
    public Utente(String email, String password, String cognome, String nome) {
        this.email = email;
        this.password = password;
        this.cognome = cognome;
        this.nome = nome;
    }

    public Utente() {

    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
