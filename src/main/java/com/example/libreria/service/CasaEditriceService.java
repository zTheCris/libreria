package com.example.libreria.service;

import com.example.libreria.repository.CasaEditriceRepository;
import org.springframework.stereotype.Service;

@Service
public class CasaEditriceService {
    private final CasaEditriceRepository casaEditriceRepository;

    public CasaEditriceService(CasaEditriceRepository casaEditriceRepository) {
        this.casaEditriceRepository = casaEditriceRepository;
    }
    // Metodi
}
