package com.example.libreria.service;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.model.Copia;
import com.example.libreria.repository.CopiaRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CopieService {
    private final CopiaRepository copiaRepository;
    private final Mapper mapper;
    // Constructor Injection
    public CopieService(CopiaRepository copiaRepository,Mapper mapper) {
        this.copiaRepository = copiaRepository;
        this.mapper = mapper;
    }


    // Metodi
    public List<CopiaDTO> getAll(){
        List<Copia> rispostaDB = copiaRepository.findAll();
        List<CopiaDTO> copieDTOret = new ArrayList<>();
        for (Copia copia:rispostaDB)
            copieDTOret.add(mapper.convertCopieToCopieDTO(copia));
        return copieDTOret;
    }

    public List<CopiaDTO> getAllByNumeroCopie(Integer numeroCopie){
        List<Copia> rispostaDB = copiaRepository.findByNumeroCopie(numeroCopie);
        List<CopiaDTO> copieDTOret = new ArrayList<>();
        for (Copia copia:rispostaDB)
            copieDTOret.add(mapper.convertCopieToCopieDTO(copia));
        return copieDTOret;
    }

}
