package com.example.libreria.controller;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.model.Autore;
import com.example.libreria.model.Nazionalita;
import com.example.libreria.repository.NazionalitaRepository;
import com.example.libreria.service.AutoreService;
import com.example.libreria.utility.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    // DELETE
}
