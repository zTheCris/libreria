package com.example.libreria.repository;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.model.CasaEditrice;
import com.example.libreria.model.Copia;
import com.example.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopiaRepository extends JpaRepository<Copia, Integer> {
    List<Copia> findByNumeroCopie(Integer numeroCopie);

    List<Copia> findAllByCasaEditrice(CasaEditrice casaEditrice);

    @Modifying
    @Query(value = """
                DELETE FROM Copie WHERE Copie.fk_isbn = :isbn
            """, nativeQuery = true)
    void deleteLibroFromCopie(@Param("isbn") Integer isbn);

    List<Copia> findAllByLibro(Libro libro);
}
