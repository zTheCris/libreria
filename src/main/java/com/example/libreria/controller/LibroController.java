package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.dto.request.LibroRequest;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.exception.LibroAlreadyExistsException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.model.Autore;
import com.example.libreria.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @GetMapping("/all")
    public List<LibroDTO> getAllDTO() {
        return libroService.getAll();
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
    public ResponseEntity<?> getByTitolo(@PathVariable String titolo) {
        try {
            return ResponseEntity.ok(libroService.getByTitolo(titolo));
        } catch (LibroNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // libro by title
    @GetMapping("/getbytitle/{titolo}")
    public ResponseEntity<?> getByTitoloEsatto(@PathVariable String titolo){
        try{
            return ResponseEntity.ok(libroService.getByTitoloEsatto(titolo));
        }catch (LibroNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getbyautore/{id}")
    public ResponseEntity<List<LibroDTO>> getByAutore(@PathVariable Integer id){
        try{
            Set<Integer> autoriIDs = new HashSet<>();
            autoriIDs.add(id);
            // Dato che è solo uno, passo un set a un parametro
            return ResponseEntity.ok(libroService.getByAutori(autoriIDs));
        } catch (AutoreNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    //POST

    @PostMapping
    // Grazie al ?, posso evitare di specificare il tipo di body che vuole la ResponseEntity.
    public ResponseEntity<?> addLibro(@RequestBody LibroRequest libroRequest) {
        try{
            return ResponseEntity.ok(libroService.addLibro(libroRequest));
        } catch (GenereNotFoundException | AutoreNotFoundException e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (LibroAlreadyExistsException e){
            // Il codice HTTP che identifica un dato già esistente, è 409, CONFLICT
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // PUT
    // modify by isbn
    @PutMapping("/{isbn}")
    public ResponseEntity<?> modificaLibro(@PathVariable Integer isbn,@RequestBody LibroRequest libroRequest) {
        try {

            return ResponseEntity.ok(libroService.modificaLibro(isbn, libroRequest));
        } catch (GenereNotFoundException | AutoreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    // delete By Isbn
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable Integer isbn){
        try{
            libroService.deleteLibro(isbn);
            return ResponseEntity.ok("Libro modificato.");
        } catch(LibroNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
