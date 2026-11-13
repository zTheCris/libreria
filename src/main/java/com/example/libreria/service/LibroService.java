package com.example.libreria.service;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.dto.request.LibroRequest;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.exception.LibroAlreadyExistsException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.model.Autore;
import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.AutoreRepository;
import com.example.libreria.repository.CopiaRepository;
import com.example.libreria.repository.GenereRepository;
import com.example.libreria.repository.LibroRepository;

import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final GenereRepository genereRepository;
    private final AutoreRepository autoreRepository;
    private final Mapper mapper;
    private final CopiaRepository copiaRepository;


    public LibroService(LibroRepository libroRepository, GenereRepository genereRepository, AutoreRepository autoreRepository, Mapper mapper, CopiaRepository copiaRepository) {
        this.libroRepository = libroRepository;
        this.genereRepository = genereRepository;
        this.autoreRepository = autoreRepository;
        this.mapper = mapper;
        this.copiaRepository = copiaRepository;
    }

    /*
 ========================================
                GET
 ========================================
  */

    /**
     * Restituisce tutti i libri del Database attraverso {@code libroRepository.}
     * @return la lista di tutti i libri, lista vuota se non presenti.
     */
    public List<LibroDTO> getAll() {
        List<Libro> rispostaDB = libroRepository.findAll();
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro : rispostaDB) {
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        }
        return libriDTOres;
    }

    /**
     * Restituisce il Libro dato l'ISBN.
     * @param isbn il codice ISBN del Libro.
     * @return l'oggetto Libro trovato nel Database.
     * @throws LibroNotFoundException se l'ISBN non esiste nel Database.
     */
    public LibroDTO getByIsbn(Integer isbn) {
        Libro libro = libroRepository.findById(isbn).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));
        return mapper.convertLibroToLibroDTO(libro);
    }


//    public List<Libro> getAllLibro(){
//        return libroRepository.findAll();
//    }
    /**
     * Restituisce tutti i libri nel Database se la Stringa ({@code titolo}) è in uno o più record.
     * @return la lista di tutti i libri che contengono la Stringa titolo nel campo {@code titolo}, lista vuota se non presenti.
     * @throws LibroNotFoundException se il Libro con il titolo non è stato trovato nel Database.
     */
    public List<LibroDTO> getByTitolo(String titolo) {
        List<Libro> rispostaDB = libroRepository.findByTitoloContainsIgnoreCase(titolo);
        // Dato che questo metodo mi restituisce solo un array vuoto se il titolo non c'è nel db, la devo gestire io.
        if (rispostaDB.isEmpty()) throw new LibroNotFoundException("Libro non trovato.");
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro : rispostaDB)
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        return libriDTOres;

    }

    /**
     * Restituisce tutti i libri nel Database in base al titolo ESATTO.
     * @return la lista di tutti i libri filtrati per titolo, lista vuota se non presenti.
     * @throws LibroNotFoundException se il Libro con il titolo ESATTO non è stato trovato nel Database.
     */
    public LibroDTO getByTitoloEsatto(String titolo) {
        // 1. Chiamo il DB.
        // 2. Vedo se il libro esiste.
        // 3. Se non esiste, lancio un eccezione che verrà gestita dal GlobalExceptionHandler del RestController.
        // 4. Se lo trova, converto la risposta del database (Libro) nel suo DTO.
        // 5. Lo rimando indietro.
        return mapper.convertLibroToLibroDTO(libroRepository.findByTitoloIgnoreCase(titolo).orElseThrow(() -> new LibroNotFoundException("Libro non trovato")));
    }

    /**
     * Restituisce tutti i libri nel Database che sono stati scritti da N autori, dove N è il numero deegli elementi di {@code autoriIDS.}
     * @return la lista di tutti i libri filtrati per titolo, lista vuota se non presenti.
     * @throws AutoreNotFoundException se l'autore non esiste nel Database.
     */

    public List<LibroDTO> getByAutori(Set<Integer> autoriIDS){
        Set<Autore> autoriDB = new HashSet<>(autoreRepository.findAllById(autoriIDS));
        // Controllo se gli autori esistono, altrimenti eccezione
        if (autoriIDS.size() != autoriDB.size())
            // Qualche autore o nessun autore non è stato trovato.
            throw new AutoreNotFoundException("Uno o più autori inseriti non sono nel Database. Riprovare");

        /*
        Il problema qui sta che nella entity io ho definito gli autori come Set.
        Dato che una Set non permette duplicati, la query che devo fare per farmi restituire dal DB
        Una lista di Autori senza duplicati, devo usare la method query con la DISTINCT.
         */
        List<Libro> rispostaDB = libroRepository.findDistinctByAutoriIn(autoriDB);

        // Lo converto in DTO
        List<LibroDTO> libriDTO = new ArrayList<>();

        for (Libro libro:rispostaDB)
            libriDTO.add(mapper.convertLibroToLibroDTO(libro));

        return libriDTO;
    }
    /*
    ========================================
                   POST
    ========================================
     */
    // AggiungiLibro

    /**
     * Aggiunge un Libro al Database, inserendo anche il Genere se non esiste. Il {@code libroRequest} che arriva in ingresso viene poi
     * controllato. Se il libro già esiste, è più opportuno passare per il metodo modificaLibro.
     * E' OBBLIGATORIO per l'inserimento di un Libro, che prima si inserisca l'Autore attraverso {@code AutoreService}.
     * @param libroRequest il libro che il Client vuole salvare nel Database.
     * @return il Libro salvato, convertito in DTO.
     * @throws LibroAlreadyExistsException se il libro già esiste nel database.
     * @throws AutoreNotFoundException se gli autori nel database dati dalla lista {@code autoriIDs} non sono stati trovati.
     */
    public LibroDTO addLibro(LibroRequest libroRequest) {
        // Prima di tutto, poichè nel database ISBN non è auto increment, devo vedere se quel libro esiste.
        if(libroRepository.findById(libroRequest.getIsbn()).isPresent())
            // Se esiste, sarebbe piu' opportuno passare dal metodo modificaLibro
            throw new LibroAlreadyExistsException("Libro già esistente. Modificare attraverso metodo PUT.");

        // Se il genere che mi ha mandato non lo trovo, lo inserisco nel DB.
        Genere genereObj = genereRepository.findByNomeIgnoreCase(libroRequest.getGenere())
                // Optional ha un metodo oltre alla orElseThrow che invece di lanciare un eccezione, esegue qualcosa.
                .orElseGet(()-> genereRepository.save(new Genere(libroRequest.getGenere())));
        // Ora controllo se gli autori che mi sono stati mandati esistono davvero nel Database.
        List<Autore> autori = autoreRepository.findAllById(libroRequest.getAutoriIDs());
        if(autori.size() !=libroRequest.getAutoriIDs().size())
            // Dato che nell'oggetto di richiesta gli mando solo gli id, non ho le informazioni per crearne uno nuovo.
            // Devo mandare un eccezione
            throw new AutoreNotFoundException("Uno o più autori inseriti non sono nel database.");
        // Ora che ho verificato tutto, creo il Libro con i campi mandati da libroRequest.
        Libro libro = new Libro(libroRequest.getIsbn(), libroRequest.getTitolo(), libroRequest.getData(), libroRequest.getPrezzo(), genereObj);
        libro.setAutori(new HashSet<>(autori)); // Con IntelliJ ho fatto .toSet e il cast di una List a Set è questo:

        // Ora ritorno al Client il Libro che ha salvato convertito in DTO
        return mapper.convertLibroToLibroDTO(libroRepository.save(libro));

    }




    /*
    ========================================
                   PUT
    ========================================
     */

    /**
     * Modifica un Libro nel database. Esegue gli stessi passaggi della {@code addLibro()}, ma con la differenza che prima controlla se il Libro
     * esiste nel Database.
     * @param libroRequest il libro che il Client vuole modificare.
     * @return il libro modificato nel Database.
     * @throws LibroNotFoundException se il {@code Libro} non è stato trovato nel Database.
     * @throws AutoreNotFoundException se uno o più autori non sono stati trovati nel Database.
     */
    public LibroDTO modificaLibro(LibroRequest libroRequest) {
        // Vedo se trovo il libro.
        libroRepository.findById(libroRequest.getIsbn()).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));

        // Vedo se trovo il genere che mi ha passato il Client
        Genere genereObj = genereRepository.findByNomeIgnoreCase(libroRequest.getGenere())
                // Optional ha un metodo oltre alla orElseThrow che invece di lanciare un eccezione, esegue qualcosa.
                .orElseGet(()-> genereRepository.save(new Genere(libroRequest.getGenere())));
        // Ho tutto, posso creare l'oggetto.
        Libro libro = new Libro(libroRequest.getIsbn(), libroRequest.getTitolo(), libroRequest.getData(), libroRequest.getPrezzo(), genereObj);

        // Setto gli autori
        List<Autore> autori = autoreRepository.findAllById(libroRequest.getAutoriIDs());

        // Controllo se TUTTI gli autori che mi ha passato il client esistono
        if (autori.size() != libroRequest.getAutoriIDs().size())
            // Qualche autore o nessun autore non è stato trovato.
            throw new AutoreNotFoundException("Uno o più autori inseriti non sono nel Database. Riprovare");

        // Setto gli autori del libro
        libro.setAutori(new HashSet<>(autori));

        // Ora che ho tutto, setto l'autore e aggiorno con save
        return mapper.convertLibroToLibroDTO(libroRepository.save(libro));
    }
    /*
    ========================================
                   DELETE
    ========================================
     */

    /**
     * Elimina il Libro dal database in base al codice {@code ISBN} del Libro,
     * Visto che le tabelle {@code Copie} e {@code Libri_Autori} dipendono da {@code Libro}, è opportuno rimuovere i record sia in Copie sia in Libri_Autori,
     * cosi da rispettare la coerenza del database.
     * Il metodo prevede l'utilizzi di {@code @Transactional} poiché si stanno eseguendo query su più tabelle, cosi da evitare
     * incongruenza tra le varie tabelle.
     * @param isbn l'ISBN del Libro da eliminare
     */
    // Poichè è un operazione che comprende piu' entità, è meglio usare la transazione.
    @Transactional
    public void deleteLibro(Integer isbn) {
        /*
        Dato che il libro spunta piu' volte all'interno di altre tabelle,
        cancello direttamente i record da quelle tabelle per mantenere il database consistente.
        In ordine: Libri_Autori, Copie.
        */
        // Controllo se il libro che mi ha passato esiste e
        Libro libro = libroRepository.findById(isbn).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));
        // Elimino da LibriAutori
        libroRepository.deleteLibroFromLibriAutori(isbn);
        // Elimino da copie
        copiaRepository.deleteLibroFromCopie(isbn);
        // Finalmente, elimino il libro.
        libroRepository.delete(libro);
    }
    /*
    Il modo per poterlo fare con una delete, è dire a Hibernate tutti i riferimenti in cui
    Libro compare in altre tabelle, mappandoli con @OneToMany o @ManyToMany usando il mappedBy.
    Insieme al mappedBy, si può usare l'attributo cascade all'interno dei parametri delle Annotazioni
    per dire a Hibernate che quando viene effettuata una DELETE, bisogna fare un eliminazione a cascata.
     */


}
