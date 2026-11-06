package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.model.Libro;
import com.example.libreria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/libri")
public class LibroController {
    @Autowired
    private LibroService libroService;
    @GetMapping("/addLibro")
    public LibroDTO addLibro(Integer isbn, String titolo, LocalDate anno, Integer prezzo, String genere){
        return libroService.addLibro(isbn,titolo,anno,prezzo,genere);
    }
    // TEMPORANEO
    @GetMapping("/allDTO")
    public List<LibroDTO> getAllDTO(){
        return libroService.getAll();
    }


    @GetMapping("/all")
    public List<Libro> getAll(){
        return libroService.getAllLibro();
    }
}
