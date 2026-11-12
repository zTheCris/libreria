package com.example.libreria.service;

import com.example.libreria.dto.GenereDTO;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.model.Genere;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenereService {
    private final GenereRepository genereRepository;
    private final Mapper mapper;

    public GenereService(GenereRepository genereRepository, Mapper mapper) {
        this.genereRepository = genereRepository;
        this.mapper = mapper;
    }

    public List<GenereDTO> getAll(){
        List<Genere> rispostaDB = genereRepository.findAll();
        if(rispostaDB.isEmpty()) throw new GenereNotFoundException("Non ci sono generi.");
        List<GenereDTO> generiDTOret = new ArrayList<>();
        for (Genere genere: rispostaDB) {
            generiDTOret.add(mapper.convertGenereToGenereDTO(genere));
        }
        return generiDTOret;
    }

    public GenereDTO addGenere(String nome){
       return mapper.convertGenereToGenereDTO(genereRepository.save(new Genere(nome)));
    }

    public GenereDTO getById(Integer id) {
        return mapper.convertGenereToGenereDTO(genereRepository.findById(id).orElseThrow(()-> new AutoreNotFoundException("Autore non trovato con id "+id)));

    }

    public GenereDTO modificaGenere(Integer id, String nuovoNome) {
        Genere genere = genereRepository.findById(id).orElseThrow(()-> new GenereNotFoundException("Genere con id "+ id +" non trovato." ));
        genere.setNome(nuovoNome);
        return mapper.convertGenereToGenereDTO(genereRepository.save(genere));
    }

    public void deleteGenere(Integer id) {
        // Controllo in piu' se il genere esiste
        Genere genere = genereRepository.findById(id).orElseThrow(()-> new GenereNotFoundException("Genere non trovato"));
        genereRepository.delete(genere);
    }
}
