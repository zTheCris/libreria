
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
import org.springframework.transaction.annotation.Transactional;

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
        // Ritorno tutti i libri in un DTO. Utilizzo gli Streams per poter eseguire operazioni sulle Collections.
        return autoreRepository.findAll().stream().map(mapper::convertAutoreToAutoreDTO).toList();
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
        return autoreRepository.findAllByNomeAndCognomeIgnoreCase(nome,cognome).stream().map(mapper::convertAutoreToAutoreDTO).toList();
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
    public List<AutoreDTO> getByNome(String nome) {
        return autoreRepository.findAllByNomeContainingIgnoreCase(nome).stream().map(mapper::convertAutoreToAutoreDTO).toList();
    }
    public Long getNumAutori() {
        return autoreRepository.count();
    }


    /**
     * Inserisce un nuovo autore nel database, associandogli la nazionalità specificata.
     * Se la nazionalità non esiste, viene creata e salvata automaticamente.
     *
     * @param autoreRequest dati dell'autore che il client desidera inserire.
     * @return autore salvato nel database, convertito nel rispettivo DTO.
     */
    @Transactional
    public AutoreDTO addAutore(AutoreRequest autoreRequest) {
        // Prima di tutto devo cercare se la nazionalità che il Client mi ha mandato esite.
        Nazionalita nazionalitaObj = nazionalitaRepository
                .findByNomeIgnoreCase(autoreRequest.getNazionalita())
                .orElseGet(() -> nazionalitaRepository.save(new Nazionalita(autoreRequest.getNazionalita())));

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
    @Transactional
    public AutoreDTO modificaAutore(Integer idAutore, AutoreRequest autoreRequest) {
        Autore autore = autoreRepository.findById(idAutore)
                .orElseThrow(() -> new AutoreNotFoundException("Autore non trovato."));

        Nazionalita nazionalitaObj = nazionalitaRepository.save(new Nazionalita(autoreRequest.getNazionalita()));

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
    @Transactional
    public void deleteByIdAutore(Integer idAutore) {
        Autore autore = autoreRepository.findById(idAutore)
                .orElseThrow(() -> new AutoreNotFoundException("Autore non trovato"));
        // Hibernate farà automaticamente la DELETE anche dalla tabella Libri_Autori.
        autoreRepository.delete(autore);
    }




}
