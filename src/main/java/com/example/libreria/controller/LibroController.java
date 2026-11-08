package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.service.LibroService;
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
    public LibroDTO getByIsbn(@PathVariable Integer isbn){
        return libroService.getByIsbn(isbn);
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
    public List<LibroDTO> getByTitolo(@PathVariable String titolo) {
        return libroService.getByTitolo(titolo);
    }

    // libro by title
    @GetMapping("/getbytitle/{titolo}")
    public LibroDTO getByTitoloEsatto(@PathVariable String titolo){
        return libroService.getByTitoloEsatto(titolo);
    }


    //POST

    @PostMapping("/addLibro")
    public LibroDTO addLibro(Integer isbn, String titolo, LocalDate data, Integer prezzo, String genere, @RequestBody Set<Integer> autoriIDs) {
        return libroService.addLibro(isbn, titolo, data, prezzo, genere, autoriIDs);
    }

    // PUT
    @PutMapping("{isbn}")
    public LibroDTO modificaLibro(@PathVariable Integer isbn, String titolo, LocalDate data, Integer prezzo, String genere, @RequestBody Set<Integer> autoriIDs){
        return libroService.modificaLibro(isbn,titolo,data,prezzo,genere,autoriIDs);
    }
    // DELETE
    @DeleteMapping("{isbn}")
    public void deleteLibro(@PathVariable Integer isbn){
        libroService.deleteLibro(isbn);
    }
}
