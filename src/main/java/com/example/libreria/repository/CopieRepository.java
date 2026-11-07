package com.example.libreria.repository;

import com.example.libreria.model.Copia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopieRepository extends JpaRepository<Copia, Integer> {
}
