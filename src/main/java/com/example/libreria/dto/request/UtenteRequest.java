package com.example.libreria.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UtenteRequest {
    @Email(message = "Lo username deve essere un'email valida.")
    @NotBlank(message = "Lo username non è stato inserito.")
    private String username;

    @NotBlank(message = "La password non è stata inserita.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UtenteRequest() {
    }

    public UtenteRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
