package com.example.libreria.repository;

import com.example.libreria.model.Copia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CopiaRepository extends JpaRepository<Copia, Integer> {
    List<Copia> findByNumeroCopie(Integer numeroCopie);

}
