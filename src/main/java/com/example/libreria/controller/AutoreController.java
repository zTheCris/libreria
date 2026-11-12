package com.example.libreria.controller;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.dto.request.AutoreRequest;
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
    public ResponseEntity<?> addAutore(@RequestBody AutoreRequest autoreRequest) {
        try {
            return ResponseEntity.ok(autoreService.addAutore(autoreRequest));
        } catch (NazionalitaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT
    @PutMapping("/{idAutore}")
    public ResponseEntity<?> modificaAutore(@PathVariable Integer idAutore, @RequestBody AutoreRequest autoreRequest) {
        try{
            return ResponseEntity.ok(autoreService.modificaAutore(idAutore, autoreRequest));
        } catch (NazionalitaNotFoundException | AutoreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{idAutore}")
    public ResponseEntity<?> deleteAutore(@PathVariable Integer idAutore) {
        try{
            autoreService.deleteByIdAutore(idAutore);
            return ResponseEntity.ok("Autore rimosso correttamente.");
        } catch (AutoreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

