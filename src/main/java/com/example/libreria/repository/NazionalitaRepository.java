package com.example.libreria.repository;

import com.example.libreria.model.Nazionalita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NazionalitaRepository extends JpaRepository<Nazionalita,Integer> {
    Optional<Nazionalita> findByNomeIgnoreCase(String nome);
}
