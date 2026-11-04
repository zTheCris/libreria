package com.example.libreria.service;

import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private GenereRepository genereRepository;
    // AggiungiLibro

    public Libro addLibro(Integer isbn,String titolo, LocalDate anno, Integer prezzo, String genere){
        try {
            Genere genereObj = genereRepository.findByNome(genere).orElseThrow( () -> new RuntimeException("Genere non trovato"));
            Libro libroDaAggiungere = new Libro(isbn, titolo, anno, prezzo,genereObj);
            return libroRepository.save(libroDaAggiungere);
        } catch (RuntimeException e){
            // Entrerà qui solamente quando il genere non è stato trovato
            e.printStackTrace();
            return null;
        }
    }

}
