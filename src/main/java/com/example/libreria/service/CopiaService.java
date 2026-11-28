package com.example.libreria.service;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.dto.request.CopiaRequest;
import com.example.libreria.exception.CasaEditriceNotFound;
import com.example.libreria.exception.CopiaNotFoundException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.model.CasaEditrice;
import com.example.libreria.model.Copia;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.CasaEditriceRepository;
import com.example.libreria.repository.CopiaRepository;
import com.example.libreria.repository.LibroRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CopiaService {

    private final CopiaRepository copiaRepository;
    private final Mapper mapper;
    private final CasaEditriceRepository casaEditriceRepository;
    private final LibroRepository libroRepository;

    // Constructor Injection
    public CopiaService(CopiaRepository copiaRepository, Mapper mapper, CasaEditriceRepository casaEditriceRepository, LibroRepository libroRepository) {
        this.copiaRepository = copiaRepository;
        this.mapper = mapper;
        this.casaEditriceRepository = casaEditriceRepository;
        this.libroRepository = libroRepository;
    }


    // Metodi

    /**
     * Restituisce tutte le copie del Database attraverso {@code copiaRepository.}
     *
     * @return la lista dele copie nel Database, convertite in DTO.
     */
    public List<CopiaDTO> getAll() {
      return copiaRepository.findAll().stream().map(mapper::convertCopiaToCopieDTO).toList();
    }

    /**
     * Restituisce tutte le copie che hanno un numero di copie ESATTO.
     *
     * @param numeroCopie il filtro per la ricerca delle copie.
     * @return la lista delle copie nel Database filtrate per {@code numeroCopie}, convertite in DTO.
     */
    public List<CopiaDTO> getAllByNumeroCopie(Integer numeroCopie) {
        // Dato che devo fare un controllo sulle Copie, me lo salvo in una variabile prima di mandare la risposta al Controller.
        List<Copia> rispostaDB = copiaRepository.findByNumeroCopie(numeroCopie);
        if (rispostaDB.isEmpty())
            throw new CopiaNotFoundException("Non sono presenti Copie con numero di copie " + numeroCopie);
        return rispostaDB.stream().map(mapper::convertCopiaToCopieDTO).toList();
    }

    /**
     * Restituisce tutte le copie esaurite. Riutilizzo il metodo {@code getAllByNumeroCopie()} con parametro 0.
     *
     * @return la lista di tutte le copie esaurite, convertite in DTO.
     */
    public List<CopiaDTO> getByNumeroCopieEsaurite() {
        // Posso direttamente usare il getAllByNumeroCopie cosi da non riutilizzare il codice.
        return getAllByNumeroCopie(0);

    }

    /**
     * Restituisce tutte le copie con almeno 1 copia ({@code numeroCopie}).
     *
     * @return la lista di tutte le copie dei libri disponibili, convertite in DTO..
     */
    public List<CopiaDTO> getByNumeroCopieDisponibili() {
        List<Copia> rispostaDB = copiaRepository
                .findAll()
                .stream()
                // Ora filtro per le copie maggiori di 0
                .filter(copia -> copia.getNumeroCopie() > 0)
                // Converto in lista
                .toList();
        if (rispostaDB.isEmpty()) throw new CopiaNotFoundException("Non ci sono copie disponibili");
        return rispostaDB.stream().map(mapper::convertCopiaToCopieDTO).toList();
    }

    /**
     * Restituisce tutte le copie scritte da una certa casa editrice.
     *
     * @param id l'id della casa editrice da ricercare.
     * @return le copie di una certa casa editrice.
     */
    public List<CopiaDTO> getAllByCasaEditrice(Integer id) {
        CasaEditrice casaEditrice = casaEditriceRepository.findById(id).orElseThrow(() -> new CasaEditriceNotFound("Casa editrice non trovata"));
        return copiaRepository.findAllByCasaEditrice(casaEditrice).stream().map(mapper::convertCopiaToCopieDTO).toList();
    }

    /**
     * Ritorna tutte le copie di un libro.
     *
     * @param isbn l'ISBN del libro
     * @return la lista delle copie del libro, convertite in DTO.
     */
    public List<CopiaDTO> getByIsbn(Integer isbn) {
        // Ricavo il libro, se non ho trovato il libro mando un eccezione.
        Libro libro = libroRepository.findById(isbn).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));
        return copiaRepository.findAllByLibro(libro).stream().map(mapper::convertCopiaToCopieDTO).toList();
    }


    /**
     * Aggiunge una copia al database attraverso l'oggetto CopiaRequest.
     * La copia deve soddisfare il seguente requisito:
     * ! -> Il Libro deve essere già stato inserito in Libri.
     *
     * @param copiaRequest l'oggetto CopiaRequest mandato dal Client
     * @return l'oggetto Copia salvato nel Database, convertito in DTO.
     */
    @Transactional
    public CopiaDTO addCopia(CopiaRequest copiaRequest) {
        // Controllo se il libro esiste
        Libro libro = libroRepository.findById(copiaRequest.getIsbn()).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));
        // Per la casa editrice, se non esiste nel DB la inserisco.

        CasaEditrice casaEditrice = casaEditriceRepository.findByNomeIgnoreCase(copiaRequest.getCasaEditrice())
                .orElseGet(
                        () -> addCasaEditrice(copiaRequest.getCasaEditrice())
                );
        // Ora creo la copia
        Copia copia = new Copia(copiaRequest.getNumeroCopie(), libro, casaEditrice);
        return mapper.convertCopiaToCopieDTO(copiaRepository.save(copia));
    }

    /**
     * Dato l'id della copia esistente nel database, e la Copia con i campi modificati, aggiorna la persistenza nel database.
     *
     * @param idCopia      l'ID della copia nel Database.
     * @param copiaRequest l'oggetto Copia modificato.
     * @return l'oggetto Copia mdoificato nel Database, convertito in DTO.
     */
    @Transactional
    public CopiaDTO modificaCopia(Integer idCopia, CopiaRequest copiaRequest) {
        // Controllo se la copia esiste
        Copia copia = copiaRepository.findById(idCopia).orElseThrow(() -> new CopiaNotFoundException("Copia non trovata."));

        // Controllo se il libro esiste
        Libro libro = libroRepository.findById(copiaRequest.getIsbn()).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));

        // Controllo se la nuova casa editrice esiste. Se non esiste, la creo.
        CasaEditrice casaEditrice = casaEditriceRepository.findByNomeIgnoreCase(copiaRequest.getCasaEditrice())
                .orElseGet(
                        () -> addCasaEditrice(copiaRequest.getCasaEditrice())
                );
        // Ora modifico la copia
        copia.setNumeroCopie(copiaRequest.getNumeroCopie());
        copia.setLibro(libro);
        copia.setCasaEditrice(casaEditrice);
        return mapper.convertCopiaToCopieDTO(copiaRepository.save(copia));
    }

    /**
     * Dato l'id di una copia, cancella il record della Copia nel Database
     *
     * @param idCopia l'id della Copia.
     */
    @Transactional
    public void deleteCopia(Integer idCopia) {
        copiaRepository.deleteById(idCopia);
    }

    //Metodi privati
    private CasaEditrice addCasaEditrice(String casaEditrice) {
        return casaEditriceRepository.save(new CasaEditrice(casaEditrice));
    }

    public Long getNumCopie() {
        return copiaRepository.count();
    }

    public List<CopiaDTO> getByTitoloLibro(String titolo) {
        // Devo ricavarmi i libri che contengono quel titolo
        List<Libro> libriConTitolo = libroRepository.findByTitoloContainingIgnoreCase(titolo);
        return copiaRepository.findAllByLibroIn(libriConTitolo).stream().map(mapper::convertCopiaToCopieDTO).toList();
    }
}

