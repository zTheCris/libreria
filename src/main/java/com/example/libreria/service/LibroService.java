package com.example.libreria.service;

import com.example.libreria.dto.AutoreDTO;
import com.example.libreria.dto.LibroDTO;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
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

import java.time.LocalDate;
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
    public List<LibroDTO> getAll() {
        List<Libro> rispostaDB = libroRepository.findAll();
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro : rispostaDB) {
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        }
        return libriDTOres;
    }

//    public List<Libro> getAllLibro(){
//        return libroRepository.findAll();
//    }

    public List<LibroDTO> getByTitolo(String titolo) {
        List<Libro> rispostaDB = libroRepository.findByTitoloContainsIgnoreCase(titolo);
        List<LibroDTO> libriDTOres = new ArrayList<>();
        for (Libro libro : rispostaDB)
            libriDTOres.add(mapper.convertLibroToLibroDTO(libro));
        return libriDTOres;

    }

    public LibroDTO getByTitoloEsatto(String titolo) {
        // 1. Chiamo il DB.
        // 2. Vedo se il libro esiste.
        // 3. Se non esiste, lancio un eccezione (potrebbe migliorare trasformando l'oggetto in un ResponseEntity, ma per ora non ci preoccupiamo del frontend).
        // 4. Se lo trova, converto con il mapper in DTO.
        // 5. Lo rimando indietro.
        return mapper.convertLibroToLibroDTO(libroRepository.findByTitolo(titolo).orElseThrow(() -> new LibroNotFoundException("Libro non trovato")));
    }

    /*
    ========================================
                   POST
    ========================================
     */
    // AggiungiLibro
    public LibroDTO addLibro(Integer isbn, String titolo, LocalDate anno, Integer prezzo, String genere, Set<Integer> autoriIDs) {

        // Per prima cosa, controllo se gli autori che mi sono stati mandati esistono davvero nel Database.
        List<Autore> autori = autoreRepository.findAllById(autoriIDs);

        // Adesso però devo vedere se effettivamente se TUTTI gli autori che mi ha mandato sono autori esistenti.
        // Per non richiamare il database ogni volta per vedere se gli autori esistono, controllo se gli elementi di autoriIDs sono tanti quanti autori.
        if (autori.size() != autoriIDs.size())
            // Qualche autore o nessun autore non è stato trovato.
            throw new AutoreNotFoundException("Uno o più autori inseriti non sono nel Database. Riprovare");

        // Ora vado avanti e creo il libro
        Genere genereObj = genereRepository.findByNome(genere).orElseThrow(() -> new GenereNotFoundException("Genere non trovato"));
        Libro libro = new Libro(isbn, titolo, anno, prezzo, genereObj);
        libro.setAutori(new HashSet<>(autori)); // Con IntelliJ ho fatto .toSet e il cast di una List a Set è questo:

        // Ora ritorno al Client il Libro che ha salvato convertito in DTO
        return mapper.convertLibroToLibroDTO(libroRepository.save(libro));

    }


    public LibroDTO getByIsbn(Integer isbn) {
        Libro libro = libroRepository.findById(isbn).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));
        return mapper.convertLibroToLibroDTO(libro);
    }


    /*
    ========================================
                   PUT
    ========================================
     */

    public LibroDTO modificaLibro(Integer isbn, String titolo, LocalDate data, Integer prezzo, String genere, Set<Integer> autoriIDs) {
        // Vedo se trovo il libro.
        libroRepository.findById(isbn).orElseThrow(() -> new LibroNotFoundException("Libro non trovato."));

        // Vedo se trovo il genere che mi ha passato il Client
        Genere genereObj = genereRepository.findByNome(genere).orElseThrow(() -> new GenereNotFoundException("Genere non trovato"));

        // Ho tutto, posso creare l'oggetto.
        Libro libro = new Libro(isbn, titolo, data, prezzo, genereObj);

        // Setto gli autori
        List<Autore> autori = autoreRepository.findAllById(autoriIDs);

        // Controllo se TUTTI gli autori che mi ha passato il client esistono
        if (autori.size() != autoriIDs.size())
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

    // Poichè è un operazione che comprende piu' entità, è meglio usare la transazione.
    @Transactional
    public void deleteLibro(Integer isbn) {
        /*
        Dato che il libro spunta piu' volte all'interno di altre tabelle,
        cancello direttamente i record da quelle tabelle per mantenere il database consistente.
        In ordine: Libri_Autori, Copie.
        */
        // Controllo se il libro che mi ha passato esiste
        libroRepository.findById(isbn).orElseThrow(()-> new LibroNotFoundException("Libro non trovato."));
        // Elimino da LibriAutori
        libroRepository.deleteLibroFromLibriAutori(isbn);
        // Elimino da copie
        copiaRepository.deleteLibroFromCopie(isbn);
        // Finalmente, elimino il libro.
        libroRepository.deleteById(isbn);
    }
    /*
    Il modo per poterlo fare con una delete, è dire a Hibernate tutti i riferimenti in cui
    Libro compare in altre tabelle, mappandoli con @OneToMany o @ManyToMany usando il mappedBy.

     */



}
