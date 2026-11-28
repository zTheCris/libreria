package com.example.libreria.repository;

import com.example.libreria.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,Integer> {
    Optional<Utente> findByEmail(String email);

    String email(String email);
}
