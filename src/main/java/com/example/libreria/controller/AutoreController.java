package com.example.libreria.controller;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.dto.StringDTO;
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
    @GetMapping()
    public ResponseEntity<List<AutoreDTO>> getAll() {
        return ResponseEntity.ok(autoreService.getAll());
    }

    @GetMapping("/num")
    public ResponseEntity<Long> getNumLibri(){
        return ResponseEntity.ok(autoreService.getNumAutori());
    }

    @GetMapping("/{nome}/{cognome}")
    public ResponseEntity<List<AutoreDTO>> getAllByNomeCognome(@PathVariable String nome,@PathVariable String cognome){
        return ResponseEntity.ok(autoreService.getByNomeCognome(nome,cognome));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<AutoreDTO>> getAllByNome(@PathVariable String nome){
        return ResponseEntity.ok(autoreService.getByNome(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoreDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(autoreService.getById(id));
    }


    // POST
    @PostMapping
    public ResponseEntity<AutoreDTO> addAutore(@RequestBody AutoreRequest autoreRequest) {
        return ResponseEntity.ok(autoreService.addAutore(autoreRequest));
    }

    // PUT
    @PutMapping("/{idAutore}")
    public ResponseEntity<AutoreDTO> modificaAutore(@PathVariable Integer idAutore, @RequestBody AutoreRequest autoreRequest) {
        return ResponseEntity.ok(autoreService.modificaAutore(idAutore, autoreRequest));
    }

    // DELETE
    @DeleteMapping("/{idAutore}")
    public ResponseEntity<StringDTO> deleteAutore(@PathVariable Integer idAutore) {
        autoreService.deleteByIdAutore(idAutore);
        return ResponseEntity.ok(new StringDTO("Autore rimosso correttamente."));
    }
}

