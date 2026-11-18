package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.dto.request.LibroRequest;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.exception.LibroAlreadyExistsException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.model.Autore;
import com.example.libreria.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/libri")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }


    // GET

    // libro by isbn
    @GetMapping("/{isbn}")
    // Grazie al ?, posso evitare di specificare il tipo di body che vuole la ResponseEntity.
    public ResponseEntity<?> getByIsbn(@PathVariable Integer isbn){
        try{
            // Nel body di risposta, torno il risultato del service.
            return ResponseEntity.ok(libroService.getByIsbn(isbn));
        } catch(LibroNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // all
    @GetMapping
    public List<LibroDTO> getAllDTO() {
        return libroService.getAll();
    }
    @GetMapping("/num")
    public ResponseEntity<Long> getNumLibri(){
        return ResponseEntity.ok(libroService.getNumLibri());
    }

    // GetAll con ricorsione
    //    @GetMapping("/all")
    //    public List<Libro> getAll(){
    //        return libroService.getAllLibro();
    //    }
    /**
    La ricerca per titolo è PARZIALE. Per la ricerca completa, usare il metodo getByTitoloEsatto
     */
    @GetMapping("/getallbytitle/{titolo}")
    public ResponseEntity<List<LibroDTO>> getByTitolo(@PathVariable String titolo) {
       return ResponseEntity.ok(libroService.getByTitolo(titolo));
    }

    // libro by title
    @GetMapping("/getbytitle/{titolo}")
    public ResponseEntity<LibroDTO> getByTitoloEsatto(@PathVariable String titolo){
        return ResponseEntity.ok(libroService.getByTitoloEsatto(titolo));
    }
    @GetMapping("/getbyautore/{id}")
    public ResponseEntity<List<LibroDTO>> getByAutore(@PathVariable Integer id){
        // Per non creare lo stesso metodo due volte, uso il metodo getByAutori con un Set di un intero.
        Set<Integer> autoriIDs = new HashSet<>();
        autoriIDs.add(id);
        // Dato che è solo uno, passo un set a un parametro
        return ResponseEntity.ok(libroService.getByAutori(autoriIDs));
    }

    @GetMapping("/getbyprezzo/{prezzo}")
    public ResponseEntity<List<LibroDTO>> getByPrezzo(@PathVariable Integer prezzo){
        return ResponseEntity.ok(libroService.getByPrezzo(prezzo));
    }
    @GetMapping("/getbyprezzomaggiore/{prezzo}")
    public ResponseEntity<List<LibroDTO>> getByPrezzoMaggiore(@PathVariable Integer prezzo){
        return ResponseEntity.ok(libroService.getByPrezzoMaggiore(prezzo));
    }

    /**
     * Per poter usare il metodo, usare i query params in Postman. Chiamarli tutti con lo stesso nome
     * @param ids gli id passati tramite query parameters.
     */
    @GetMapping("/getbyautori")
    public ResponseEntity<List<LibroDTO>> getByAutori(@RequestParam Set<Integer> ids){
        // Dato che è solo uno, passo un set a un parametro
        return ResponseEntity.ok(libroService.getByAutori(ids));
    }

    @GetMapping("/getbygenere/{genere}")
    public ResponseEntity<List<LibroDTO>> getByGenere (@PathVariable String genere){
        return ResponseEntity.ok(libroService.getByGenere(genere));
    }


    @GetMapping("/getbyanno/{anno}")
    public ResponseEntity<List<LibroDTO>> getByAnno(@PathVariable Integer anno){
        return ResponseEntity.ok(libroService.getByAnno(anno));
    }

    //POST

    @PostMapping
    public ResponseEntity<LibroDTO> addLibro(@Valid @RequestBody LibroRequest libroRequest) {
        return ResponseEntity.ok(libroService.addLibro(libroRequest));
    }

    // PUT
    // modify by isbn
    @PutMapping()
    public ResponseEntity<LibroDTO> modificaLibro(@Valid @RequestBody LibroRequest libroRequest) {
       return ResponseEntity.ok(libroService.modificaLibro(libroRequest));
    }

    // DELETE
    // delete By Isbn
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable Integer isbn){
        libroService.deleteLibro(isbn);
        return ResponseEntity.ok("Libro cancellato");
    }
}
