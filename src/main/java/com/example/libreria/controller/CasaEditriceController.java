package com.example.libreria.controller;

import com.example.libreria.dto.CasaEditriceDTO;
import com.example.libreria.service.CasaEditriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
