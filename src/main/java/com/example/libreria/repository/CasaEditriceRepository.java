package com.example.libreria.repository;

import com.example.libreria.model.CasaEditrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CasaEditriceRepository extends JpaRepository<CasaEditrice, Integer> {
    Optional<CasaEditrice> findByNomeIgnoreCase(String casaEditrice);
}
