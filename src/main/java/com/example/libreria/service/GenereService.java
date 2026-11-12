package com.example.libreria.service;

import com.example.libreria.dto.GenereDTO;
import com.example.libreria.dto.request.GenereRequest;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.repository.LibroRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenereService {
    private final GenereRepository genereRepository;
    private final Mapper mapper;
    private final LibroRepository libroRepository;

    public GenereService(GenereRepository genereRepository, Mapper mapper, LibroRepository libroRepository) {
        this.genereRepository = genereRepository;
        this.mapper = mapper;
        this.libroRepository = libroRepository;
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

    public GenereDTO addGenere(GenereRequest genereRequest){
       return mapper.convertGenereToGenereDTO(genereRepository.save(new Genere(genereRequest.getNome())));
    }

    public GenereDTO getById(Integer id) {
        return mapper.convertGenereToGenereDTO(genereRepository.findById(id).orElseThrow(()-> new AutoreNotFoundException("Autore non trovato con id "+id)));

    }

    public GenereDTO modificaGenere(Integer id, String nuovoNome) {
        Genere genere = genereRepository.findById(id).orElseThrow(()-> new GenereNotFoundException("Genere con id "+ id +" non trovato." ));
        genere.setNome(nuovoNome);
        return mapper.convertGenereToGenereDTO(genereRepository.save(genere));
    }

    @Transactional
    public void deleteGenere(Integer id) {
        // Il genere spunta piu volte in Libro, quindi devo eliminare prima il Genere in Libro.
        Genere genere = genereRepository.findById(id).orElseThrow(()-> new GenereNotFoundException("Genere non trovato"));
        libroRepository.deleteByGenere(genere);
        // Ora cancello il Genere.
        genereRepository.delete(genere);
    }
}
