package com.example.libreria.dto;

import jakarta.validation.constraints.NotNull;

public class UtenteDTO {
    private Integer idUtenete;
    private String email;
    private String nome;
    private String cognome;
    // La password NON la faccio vedere
}
