package com.example.libreria.repository;

import com.example.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Integer> {

    List<Libro> findByTitoloContainsIgnoreCase(String titolo);
}
