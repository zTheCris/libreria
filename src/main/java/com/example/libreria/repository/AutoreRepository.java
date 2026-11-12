package com.example.libreria.repository;

import com.example.libreria.model.Autore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutoreRepository extends JpaRepository<Autore,Integer> {
    List<Autore> findAllByNomeAndCognomeIgnoreCase(String nome, String cognome);
}
