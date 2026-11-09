package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.exception.AutoreNotFoundException;
import com.example.libreria.exception.GenereNotFoundException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<LibroDTO> getByIsbn(@PathVariable Integer isbn){
        try{
            // Nel body di risposta, torno il risultato del service.
            return ResponseEntity.ok(libroService.getByIsbn(isbn));
        } catch(LibroNotFoundException e){
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<List<LibroDTO>> getByTitolo(@PathVariable String titolo) {
        try {
            return ResponseEntity.ok(libroService.getByTitolo(titolo));
        } catch (LibroNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // libro by title
    @GetMapping("/getbytitle/{titolo}")
    public ResponseEntity<LibroDTO> getByTitoloEsatto(@PathVariable String titolo){
        try{
            return ResponseEntity.ok(libroService.getByTitoloEsatto(titolo));
        }catch (LibroNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    //POST

    @PostMapping("/addLibro")
    public ResponseEntity<String> addLibro(Integer isbn, String titolo, LocalDate data, Integer prezzo, String genere, @RequestBody Set<Integer> autoriIDs) {
        try{
            libroService.addLibro(isbn, titolo, data, prezzo, genere, autoriIDs);
            return ResponseEntity.ok("Libro aggiunto correttamente.");
        } catch (GenereNotFoundException | AutoreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT
    // modify by isbn
    @PutMapping("{isbn}")
    public ResponseEntity<String> modificaLibro(@PathVariable Integer isbn, String titolo, LocalDate data, Integer prezzo, String genere, @RequestBody Set<Integer> autoriIDs) {
        try {
            libroService.modificaLibro(isbn, titolo, data, prezzo, genere, autoriIDs);
            return ResponseEntity.ok("Libro aggiunto correttamente.");
        } catch (GenereNotFoundException | AutoreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // DELETE
    // delete By Isbn
    @DeleteMapping("{isbn}")
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
