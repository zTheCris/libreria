package com.example.libreria.controller;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.service.AutoreService;
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
    public List<AutoreDTO> getAll(){
        return autoreService.getAll();
    }

    // POST
    @PostMapping("/addAutore")
    public AutoreDTO addAutore(String nome, String cognome, LocalDate data, String nazionalita){
        return autoreService.addAutore(nome,cognome,data,nazionalita);
        }
    // PUT
    @PutMapping("/{idAutore}")
    public AutoreDTO modificaAutore(@PathVariable Integer idAutore, String nome, String cognome, LocalDate data, String nazionalita){
        return autoreService.modificaAutore(idAutore,nome,cognome,data,nazionalita);
    }
    // DELETE
    @DeleteMapping("/{idAutore}")
    public void deleteAutore(@PathVariable Integer idAutore){
        autoreService.deleteByIdAutore(idAutore);
    }
}
