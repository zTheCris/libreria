package com.example.libreria.repository;

import com.example.libreria.model.Autore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoreRepository extends JpaRepository<Autore,Integer> {
}
