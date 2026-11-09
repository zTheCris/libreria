package com.example.libreria.controller;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.NazionalitaNotFoundException;
import com.example.libreria.service.AutoreService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/autori")
public class AutoreController {
    private final AutoreService autoreService;

    public AutoreController(AutoreService autoreService) {
        this.autoreService = autoreService;

    }

    // GET
    @GetMapping("/all")
    public List<AutoreDTO> getAll() {
        return autoreService.getAll();
    }

    // POST
    @PostMapping("/addAutore")
    public ResponseEntity<String> addAutore(String nome, String cognome, LocalDate data, String nazionalita) {
        try {
            autoreService.addAutore(nome, cognome, data, nazionalita);
            return ResponseEntity.ok("Autore inserito correttamente.");
        } catch (NazionalitaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT
    @PutMapping("/{idAutore}")
    public ResponseEntity<String> modificaAutore(@PathVariable Integer idAutore, String nome, String cognome, LocalDate data, String nazionalita) {
        try{
            autoreService.modificaAutore(idAutore, nome, cognome, data, nazionalita);
           return ResponseEntity.ok("Autore modificato corretamente");
        } catch (NazionalitaNotFoundException |AutoreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{idAutore}")
    public ResponseEntity<String> deleteAutore(@PathVariable Integer idAutore) {
        try{
            autoreService.deleteByIdAutore(idAutore);
            return ResponseEntity.ok("Autore rimosso correttamente.");
        } catch (AutoreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

