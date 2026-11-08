package com.example.libreria.service;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.dto.LibroDTO;
import com.example.libreria.model.Autore;
import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.AutoreRepository;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.repository.LibroRepository;

import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final GenereRepository genereRepository;
    private final AutoreRepository autoreRepository;
    private final Mapper mapper;


    public LibroService(LibroRepository libroRepository, GenereRepository genereRepository,AutoreRepository autoreRepository,Mapper mapper) {
        this.libroRepository = libroRepository;
        this.genereRepository = genereRepository;
        this.autoreRepository = autoreRepository;
        this.mapper = mapper;
    }

    // AggiungiLibro

    public LibroDTO addLibro(Integer isbn, String titolo, LocalDate anno, Integer prezzo, String genere, Set<Integer> autoriIDs){
        try {
            // Per prima cosa, controllo se gli autori che mi sono stati mandati esistono davvero nel Database.
            List<Autore> autori = autoreRepository.findAllById(autoriIDs);

            // Adesso però devo vedere se effettivamente se TUTTI gli autori che mi ha mandato sono autori esistenti.
            // Per non richiamare il database ogni volta per vedere se gli autori esistono, controllo se gli elementi di autoriIDs sono tanti quanti autori.
            if(autori.size()!=autoriIDs.size())
                // Qualche autore o nessun autore non è stato trovato.
                throw new RuntimeException("Uno o più autori inseriti non sono nel Database. Riprovare");

            // Ora vado avanti e creo il libro
            Genere genereObj = genereRepository.findByNome(genere).orElseThrow( () -> new RuntimeException("Genere non trovato"));
            Libro libro = new Libro(isbn,titolo,anno,prezzo,genereObj);
            libro.setAutori(new HashSet<>(autori)); // Con IntelliJ ho fatto .toSet e mi ha fatto questo

            // Ora ritorno al Client il Libro che ha salvato convertito in DTO
            return mapper.convertLibroToLibroDTO(libroRepository.save(libro));
        } catch (RuntimeException e){
            // Se non ha trovato il genere o gli autori, oppure per altri errori a runtime, entrerà qui dentro.
            e.printStackTrace();
            return null;
        }
    }
    public List<LibroDTO> getAll(){
        List<Libro> rispostaDB = libroRepository.findAll();
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro:rispostaDB) {
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        }
        return libriDTOres;
    }

//    public List<Libro> getAllLibro(){
//        return libroRepository.findAll();
//    }

    public List<LibroDTO> getByTitolo(String titolo){
        List<Libro> rispostaDB = libroRepository.findByTitoloContainsIgnoreCase(titolo);
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro:rispostaDB)
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        return libriDTOres;

    }
}
