package com.example.libreria.controller;

import com.example.libreria.dto.CasaEditriceDTO;
import com.example.libreria.dto.StringDTO;
import com.example.libreria.dto.request.CasaEditriceRequest;
import com.example.libreria.service.CasaEditriceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case")
public class CasaEditriceController {
    private final CasaEditriceService casaEditriceService;

    public CasaEditriceController(CasaEditriceService casaEditriceService) {
        this.casaEditriceService = casaEditriceService;
    }
    @GetMapping
    public ResponseEntity<List<CasaEditriceDTO>> getAll(){
        return ResponseEntity.ok(casaEditriceService.getAll());
    }
    @GetMapping("/num")
    public ResponseEntity<Long> getNumCase(){
        return ResponseEntity.ok(casaEditriceService.getNumCase());
    }
    @GetMapping("{nome}")
    public ResponseEntity<CasaEditriceDTO> getByNome(@PathVariable String nome){
        return ResponseEntity.ok(casaEditriceService.getByNome(nome));
    }
    @PostMapping
    public ResponseEntity<CasaEditriceDTO> addCasa(@Valid @RequestBody CasaEditriceRequest casaEditriceRequest){
        return ResponseEntity.ok(casaEditriceService.addCasa(casaEditriceRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CasaEditriceDTO> modificaCasa(@PathVariable Integer id,@Valid @RequestBody CasaEditriceRequest casaEditriceRequest){
        return ResponseEntity.ok(casaEditriceService.modificaCasa(id,casaEditriceRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<StringDTO> deleteCasa(@PathVariable Integer id){
        casaEditriceService.delete(id);
        return ResponseEntity.ok(new StringDTO("Casa editrice eliminata."));
    }
}
