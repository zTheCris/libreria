package com.example.libreria.controller;

import com.example.libreria.dto.GenereDTO;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.service.GenereService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generi")
public class GenereController {
    private final GenereService genereService;

    public GenereController(GenereService genereService) {
        this.genereService = genereService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(genereService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(genereService.getById(id));
        } catch (GenereNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<String> addGenere(String nome){
        try{
            genereService.addGenere(nome);
            return ResponseEntity.ok("Genere aggiunto correttamente.");

        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/id")
    public ResponseEntity<String> modificaGenere(@PathVariable Integer id, String nuovoNome){
        try{
            genereService.modificaGenere(id,nuovoNome);
            return ResponseEntity.ok("Genere modificato correttamente");
        } catch(GenereNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
