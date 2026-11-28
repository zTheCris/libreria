package com.example.libreria.controller;

import com.example.libreria.dto.StringDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticati")
public class AutenticatiController {
    @GetMapping("/profilo")
    public String rispondi(){
        return "Wow! Sei autenticato!!!!";
    }
    @GetMapping("/ping")
    public ResponseEntity<StringDTO> ping(){
        return ResponseEntity.ok(new StringDTO("Pong!"));
    }
}
