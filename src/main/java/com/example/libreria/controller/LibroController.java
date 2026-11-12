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
import java.util.Map;
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

    //POST

    @PostMapping
    public ResponseEntity<LibroDTO> addLibro(@RequestBody LibroRequest libroRequest) {
        return ResponseEntity.ok(libroService.addLibro(libroRequest));
    }

    // PUT
    // modify by isbn
    @PutMapping()
    public ResponseEntity<LibroDTO> modificaLibro(@RequestBody LibroRequest libroRequest) {
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
