package com.example.libreria.controller;

import com.example.libreria.dto.AuthDTO;
import com.example.libreria.dto.RegistraUtenteDTO;
import com.example.libreria.dto.request.RegistraUtenteRequest;
import com.example.libreria.dto.request.UtenteRequest;
import com.example.libreria.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticazione")
public class AuthController {
    // AuthController inietta AuthService.
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody UtenteRequest utenteRequest){
        return ResponseEntity.ok(new AuthDTO(authService.autenticazione(utenteRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@RequestBody RegistraUtenteRequest registraUtenteRequest){
        return ResponseEntity.ok(new AuthDTO(authService.register(registraUtenteRequest)));
    }
}
