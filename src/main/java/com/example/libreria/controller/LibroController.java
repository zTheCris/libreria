package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.model.Libro;
import com.example.libreria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/allDTO")
    public List<LibroDTO> getAllDTO() {
        return libroService.getAll();
    }

    // GetAll con ricorsione
    //    @GetMapping("/all")
    //    public List<Libro> getAll(){
    //        return libroService.getAllLibro();
    //    }
    @GetMapping("/getbytitle/{titolo}")
    public List<LibroDTO> getByTitolo(@PathVariable String titolo) {
        return libroService.getByTitolo(titolo);
    }

    @GetMapping("")


    //POST

    @PostMapping("/addLibro")
    public LibroDTO addLibro(Integer isbn, String titolo, LocalDate data, Integer prezzo, String genere, @RequestBody Set<Integer> autoriIDs) {
        return libroService.addLibro(isbn, titolo, data, prezzo, genere, autoriIDs);
    }

    // PUT

    // DELETE

}
