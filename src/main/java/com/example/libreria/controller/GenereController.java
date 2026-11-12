package com.example.libreria.controller;

import com.example.libreria.dto.GenereDTO;
import com.example.libreria.dto.request.GenereRequest;
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
    public ResponseEntity<List<GenereDTO>> getAll(){
        return ResponseEntity.ok(genereService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GenereDTO> getById(@PathVariable Integer id){
       return ResponseEntity.ok(genereService.getById(id));
    }
    @PostMapping
    public ResponseEntity<GenereDTO> addGenere(@RequestBody GenereRequest genereRequest){
       return ResponseEntity.ok(genereService.addGenere(genereRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenereDTO> modificaGenere(@PathVariable Integer id, String nuovoNome){
        return ResponseEntity.ok(genereService.modificaGenere(id,nuovoNome));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminaGenere(@PathVariable Integer id){
        genereService.deleteGenere(id);
        return ResponseEntity.ok("Genere eliminato");
    }
}
