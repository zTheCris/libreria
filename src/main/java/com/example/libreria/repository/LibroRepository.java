package com.example.libreria.repository;

import com.example.libreria.model.Autore;
import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    List<Libro> findByTitoloContainsIgnoreCase(String titolo);

    Optional<Libro> findByTitoloIgnoreCase(String titolo);

    // Si aggiunge la modifying quando si esegue una DELETE,INSERT o una UPDATE.
    @Modifying
    @Query(value = """
                DELETE FROM Libri_Autori WHERE Libri_Autori.fk_isbn = :isbn
            """, nativeQuery = true)
    void deleteLibroFromLibriAutori(@Param ("isbn") Integer isbn);

    List<Libro> findDistinctByAutoriIn(Set<Autore> autori);

    void deleteByGenere(Genere genere);
}
