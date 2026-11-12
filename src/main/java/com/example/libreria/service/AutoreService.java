package com.example.libreria.service;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.dto.request.AutoreRequest;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.NazionalitaNotFoundException;
import com.example.libreria.model.Autore;
import com.example.libreria.model.Nazionalita;
import com.example.libreria.repository.AutoreRepository;
import com.example.libreria.repository.NazionalitaRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AutoreService {
    private final AutoreRepository autoreRepository;
    private final NazionalitaRepository nazionalitaRepository;
    private final Mapper mapper;

    public AutoreService(AutoreRepository autoreRepository, NazionalitaRepository nazionalitaRepository, Mapper mapper) {
        this.autoreRepository = autoreRepository;
        this.nazionalitaRepository = nazionalitaRepository;
        this.mapper = mapper;
    }

    public List<AutoreDTO> getAll() {
        // Ritorno tutti i libri in un DTO
        List<Autore> autoriDB = autoreRepository.findAll();
        // Adesso converto ogni autore in AutoreDTO.
        List<AutoreDTO> autoriDTO = new ArrayList<>();
        for (Autore autore : autoriDB)
            autoriDTO.add(mapper.convertAutoreToAutoreDTO(autore));
        return autoriDTO;
    }

    public AutoreDTO addAutore(AutoreRequest autoreRequest) {
        // Prima di tutto devo cercare se la nazionalità che il Client mi ha mandato esite.
        Nazionalita nazionalitaObj = nazionalitaRepository.findByNome(autoreRequest.getNazionalita())
                .orElseGet(()->addNazionalita(autoreRequest.getNazionalita()));
        // A questo punto,creo l'autore
        Autore autore = new Autore(autoreRequest.getNome(), autoreRequest.getCognome(), autoreRequest.getData(), nazionalitaObj);
        // La scelta che ho intrapreso in Libri, prevede che solo il Libro sappia da che autori è stato scritto. Quindi salvo.
        return mapper.convertAutoreToAutoreDTO(autoreRepository.save(autore));

    }

    public AutoreDTO modificaAutore(Integer idAutore, AutoreRequest autoreRequest) {
        Autore autore = autoreRepository.findById(idAutore).orElseThrow(() -> new AutoreNotFoundException("Autore non trovato."));
        Nazionalita nazionalitaObj = addNazionalita(autoreRequest.getNazionalita());

        // A questo punto,aggiorno l'autore
        autore.setNome(autoreRequest.getNome());
        autore.setCognome(autoreRequest.getCognome());
        autore.setData(autoreRequest.getData());
        autore.setNazionalita(nazionalitaObj);
        return mapper.convertAutoreToAutoreDTO(autoreRepository.save(autore));
    }



    public void deleteByIdAutore(Integer idAutore) {
        Autore autore = autoreRepository.findById(idAutore).orElseThrow(()-> new AutoreNotFoundException("Autore non trovato"));
        autoreRepository.delete(autore);
    }

    // Metodi

    /**
     * Nel caso in cui la nazionalità non esisite nel DB, lo aggiungo al database.
     * @param nazionalita la stringa contenente il nome della nazionalita
     * @return l'oggetto Nazionalita che ha salvato il database.
     */
    private Nazionalita addNazionalita(String nazionalita) {
        return nazionalitaRepository.save(new Nazionalita(nazionalita));

    }

}
