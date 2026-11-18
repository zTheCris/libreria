package com.example.libreria.service;

import com.example.libreria.dto.CasaEditriceDTO;
import com.example.libreria.model.CasaEditrice;
import com.example.libreria.repository.CasaEditriceRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaEditriceService {
    private final CasaEditriceRepository casaEditriceRepository;
    private final Mapper mapper;
    public CasaEditriceService(CasaEditriceRepository casaEditriceRepository, Mapper mapper) {
        this.casaEditriceRepository = casaEditriceRepository;
        this.mapper = mapper;
    }
    // Provo a farlo con le stream cosi da non ripetere le ripetizioni precedenti.
    public List<CasaEditriceDTO> getAll(){
        return casaEditriceRepository.findAll()
                .stream()
                .map(casaEditrice->mapper.convertCasaEditriceToCasaEditriceDTO(casaEditrice))
                .toList();
    }
    public Long getNumCase(){
        return casaEditriceRepository.count();
    }
}
