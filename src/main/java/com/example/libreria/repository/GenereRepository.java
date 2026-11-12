package com.example.libreria.repository;

import com.example.libreria.model.Genere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenereRepository extends JpaRepository<Genere,Integer> {
    // Devo trovare l'id in base al genere
    Optional<Genere> findByNomeIgnoreCase(String nome);
}
