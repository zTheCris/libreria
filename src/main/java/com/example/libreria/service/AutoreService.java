
package com.example.libreria.service;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.dto.request.AutoreRequest;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.model.Autore;
import com.example.libreria.model.Nazionalita;
import com.example.libreria.repository.AutoreRepository;
import com.example.libreria.repository.NazionalitaRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce le operazioni relative agli autori, consentendo il recupero,
 * l'inserimento, la modifica e l'eliminazione degli stessi.
 * Si occupa inoltre della gestione delle nazionalità associate agli autori.
 */
@Service
public class AutoreService {

    private final AutoreRepository autoreRepository;
    private final NazionalitaRepository nazionalitaRepository;
    private final Mapper mapper;

    /**
     * Inizializza il servizio attraverso l'iniezione delle dipendenze necessarie
     * per la gestione degli autori e delle relative nazionalità.
     *
     * @param autoreRepository repository per l'accesso ai dati degli autori.
     * @param nazionalitaRepository repository per l'accesso ai dati delle nazionalità.
     * @param mapper componente utilizzato per convertire le entità nei rispettivi DTO.
     */
    public AutoreService(AutoreRepository autoreRepository,
                         NazionalitaRepository nazionalitaRepository,
                         Mapper mapper) {
        this.autoreRepository = autoreRepository;
        this.nazionalitaRepository = nazionalitaRepository;
        this.mapper = mapper;
    }

    /**
     * Recupera tutti gli autori presenti nel database.
     *
     * @return lista di tutti gli autori convertiti nei rispettivi DTO,
     *         lista vuota se non sono presenti autori.
     */
    public List<AutoreDTO> getAll() {
        // Ritorno tutti i libri in un DTO
        List<Autore> autoriDB = autoreRepository.findAll();

        // Adesso converto ogni autore in AutoreDTO.
        List<AutoreDTO> autoriDTO = new ArrayList<>();

        for (Autore autore : autoriDB)
            autoriDTO.add(mapper.convertAutoreToAutoreDTO(autore));

        return autoriDTO;
    }

    /**
     * Recupera tutti gli autori che corrispondono al nome e al cognome specificati.
     * La ricerca non distingue tra lettere maiuscole e minuscole nel cognome.
     *
     * @param nome nome dell'autore da ricercare.
     * @param cognome cognome dell'autore da ricercare.
     * @return lista degli autori corrispondenti alla ricerca,
     *         lista vuota se non vengono trovati risultati.
     */
    public List<AutoreDTO> getByNomeCognome(String nome, String cognome) {
        List<Autore> autoriDB =
                autoreRepository.findAllByNomeAndCognomeIgnoreCase(nome, cognome);

        List<AutoreDTO> autoriDTO = new ArrayList<>();

        for (Autore autore : autoriDB)
            autoriDTO.add(mapper.convertAutoreToAutoreDTO(autore));

        return autoriDTO;
    }

    /**
     * Recupera un autore tramite il suo identificativo.
     *
     * @param id identificativo dell'autore da ricercare.
     * @return autore trovato nel database, convertito nel rispettivo DTO.
     * @throws AutoreNotFoundException se non esiste un autore con l'identificativo specificato.
     */
    public AutoreDTO getById(Integer id) {
        return mapper.convertAutoreToAutoreDTO(
                autoreRepository.findById(id)
                        .orElseThrow(() -> new AutoreNotFoundException("Autore non trovato."))
        );
    }

    /**
     * Inserisce un nuovo autore nel database, associandogli la nazionalità specificata.
     * Se la nazionalità non esiste, viene creata e salvata automaticamente.
     *
     * @param autoreRequest dati dell'autore che il client desidera inserire.
     * @return autore salvato nel database, convertito nel rispettivo DTO.
     */
    public AutoreDTO addAutore(AutoreRequest autoreRequest) {
        // Prima di tutto devo cercare se la nazionalità che il Client mi ha mandato esite.
        Nazionalita nazionalitaObj = nazionalitaRepository
                .findByNomeIgnoreCase(autoreRequest.getNazionalita())
                .orElseGet(() -> addNazionalita(autoreRequest.getNazionalita()));

        // A questo punto,creo l'autore
        Autore autore = new Autore(
                autoreRequest.getNome(),
                autoreRequest.getCognome(),
                autoreRequest.getData(),
                nazionalitaObj
        );

        // La scelta che ho intrapreso in Libri, prevede che solo il Libro sappia da che autori è stato scritto. Quindi salvo.
        return mapper.convertAutoreToAutoreDTO(autoreRepository.save(autore));
    }

    /**
     * Modifica i dati di un autore già presente nel database,
     * aggiornandone anche la nazionalità.
     *
     * @param idAutore identificativo dell'autore da modificare.
     * @param autoreRequest nuovi dati dell'autore.
     * @return autore modificato nel database, convertito nel rispettivo DTO.
     * @throws AutoreNotFoundException se non esiste un autore con l'identificativo specificato.
     */
    public AutoreDTO modificaAutore(Integer idAutore, AutoreRequest autoreRequest) {
        Autore autore = autoreRepository.findById(idAutore)
                .orElseThrow(() -> new AutoreNotFoundException("Autore non trovato."));

        Nazionalita nazionalitaObj = addNazionalita(autoreRequest.getNazionalita());

        // A questo punto,aggiorno l'autore
        autore.setNome(autoreRequest.getNome());
        autore.setCognome(autoreRequest.getCognome());
        autore.setData(autoreRequest.getData());
        autore.setNazionalita(nazionalitaObj);

        return mapper.convertAutoreToAutoreDTO(autoreRepository.save(autore));
    }

    /**
     * Elimina un autore dal database tramite il suo identificativo.
     *
     * @param idAutore identificativo dell'autore da eliminare.
     * @throws AutoreNotFoundException se non esiste un autore con l'identificativo specificato.
     */
    public void deleteByIdAutore(Integer idAutore) {
        Autore autore = autoreRepository.findById(idAutore)
                .orElseThrow(() -> new AutoreNotFoundException("Autore non trovato"));

        autoreRepository.delete(autore);
    }

    /**
     * Recupera una nazionalità tramite il suo nome oppure la inserisce nel database
     * se non è già presente.
     *
     * @param nazionalita nome della nazionalità da ricercare o inserire.
     * @return oggetto {@code Nazionalita} trovato oppure appena salvato nel database.
     */
    private Nazionalita addNazionalita(String nazionalita) {
        return nazionalitaRepository.save(new Nazionalita(nazionalita));
    }
}
