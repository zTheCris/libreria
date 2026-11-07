package com.example.libreria.service;

import com.example.libreria.repository.NazionalitaRepository;
import org.springframework.stereotype.Service;

@Service
public class NazionalitaService {
    private final NazionalitaRepository nazionalitaRepository;

    public NazionalitaService(NazionalitaRepository nazionalitaRepository) {
        this.nazionalitaRepository = nazionalitaRepository;
    }
    // Metodi

}
