package com.example.libreria.service;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.repository.LibroRepository;

import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final GenereRepository genereRepository;
    private final Mapper mapper;


    public LibroService(LibroRepository libroRepository, GenereRepository genereRepository,Mapper mapper) {
        this.libroRepository = libroRepository;
        this.genereRepository = genereRepository;
        this.mapper = mapper;
    }

    // AggiungiLibro

    public LibroDTO addLibro(Integer isbn,String titolo, LocalDate anno, Integer prezzo, String genere){
        try {
            Genere genereObj = genereRepository.findByNome(genere).orElseThrow( () -> new RuntimeException("Genere non trovato"));
            Libro libroDaAggiungere = new Libro(isbn, titolo, anno, prezzo,genereObj);
            return mapper.convertLibroToLibroDTO(libroRepository.save(libroDaAggiungere));
        } catch (RuntimeException e){
            // Entrerà qui solamente quando il genere non è stato trovato
            e.printStackTrace();
            return null;
        }
    }
    public List<LibroDTO> getAll(){
        List<Libro> rispostaDB = libroRepository.findAllBy();
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro:rispostaDB) {
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        }
        return libriDTOres;
    }

    public List<Libro> getAllLibro(){
        return libroRepository.findAll();
    }

}
