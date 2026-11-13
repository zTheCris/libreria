
package com.example.libreria.service;

import com.example.libreria.dto.GenereDTO;
import com.example.libreria.dto.request.GenereRequest;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.model.Genere;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.repository.LibroRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce le operazioni relative ai generi letterari, consentendo il recupero,
 * l'inserimento, la modifica e l'eliminazione degli stessi.
 * Si occupa inoltre di gestire le associazioni tra generi e libri.
 */
@Service
public class GenereService {

    private final GenereRepository genereRepository;
    private final Mapper mapper;
    private final LibroRepository libroRepository;

    /**
     * Inizializza il servizio attraverso l'iniezione delle dipendenze necessarie
     * per la gestione dei generi e dei libri.
     *
     * @param genereRepository repository per l'accesso ai dati dei generi.
     * @param mapper componente utilizzato per convertire le entità nei rispettivi DTO.
     * @param libroRepository repository per l'accesso ai dati dei libri.
     */
    public GenereService(GenereRepository genereRepository,
                         Mapper mapper,
                         LibroRepository libroRepository) {
        this.genereRepository = genereRepository;
        this.mapper = mapper;
        this.libroRepository = libroRepository;
    }

    /**
     * Recupera tutti i generi presenti nel database.
     *
     * @return lista di tutti i generi convertiti nei rispettivi DTO.
     * @throws GenereNotFoundException se non sono presenti generi nel database.
     */
    public List<GenereDTO> getAll() {
        List<Genere> rispostaDB = genereRepository.findAll();

        if (rispostaDB.isEmpty())
            throw new GenereNotFoundException("Non ci sono generi.");

        List<GenereDTO> generiDTOret = new ArrayList<>();

        for (Genere genere : rispostaDB) {
            generiDTOret.add(mapper.convertGenereToGenereDTO(genere));
        }

        return generiDTOret;
    }

    /**
     * Inserisce un nuovo genere nel database.
     *
     * @param genereRequest dati del genere che il client desidera inserire.
     * @return genere salvato nel database, convertito nel rispettivo DTO.
     */
    public GenereDTO addGenere(GenereRequest genereRequest) {
        return mapper.convertGenereToGenereDTO(
                genereRepository.save(new Genere(genereRequest.getNome()))
        );
    }

    /**
     * Recupera un genere tramite il suo identificativo.
     *
     * @param id identificativo del genere da ricercare.
     * @return genere trovato nel database, convertito nel rispettivo DTO.
     * @throws GenereNotFoundException se non esiste un genere con l'identificativo specificato.
     */
    public GenereDTO getById(Integer id) {
        return mapper.convertGenereToGenereDTO(
                genereRepository.findById(id)
                        .orElseThrow(() -> new GenereNotFoundException(
                                "Genere non trovato con id " + id
                        ))
        );
    }

    /**
     * Modifica il nome di un genere già presente nel database.
     *
     * @param id identificativo del genere da modificare.
     * @param nuovoNome nuovo nome da assegnare al genere.
     * @return genere modificato nel database, convertito nel rispettivo DTO.
     * @throws GenereNotFoundException se non esiste un genere con l'identificativo specificato.
     */
    public GenereDTO modificaGenere(Integer id, String nuovoNome) {
        Genere genere = genereRepository.findById(id)
                .orElseThrow(() -> new GenereNotFoundException(
                        "Genere con id " + id + " non trovato."
                ));

        genere.setNome(nuovoNome);

        return mapper.convertGenereToGenereDTO(genereRepository.save(genere));
    }

    /**
     * Elimina un genere dal database, rimuovendo preventivamente
     * le associazioni con i libri che lo utilizzano.
     *
     * @param id identificativo del genere da eliminare.
     * @throws GenereNotFoundException se non esiste un genere con l'identificativo specificato.
     */
    @Transactional
    public void deleteGenere(Integer id) {
        // Il genere spunta piu volte in Libro, quindi devo eliminare prima il Genere in Libro.
        Genere genere = genereRepository.findById(id)
                .orElseThrow(() -> new GenereNotFoundException("Genere non trovato"));

        libroRepository.deleteByGenere(genere);

        // Ora cancello il Genere.
        genereRepository.delete(genere);
    }
}
