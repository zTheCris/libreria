package com.example.libreria.controller;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.dto.request.CopiaRequest;
import com.example.libreria.exception.CasaEditriceNotFound;
import com.example.libreria.exception.CopiaNotFoundException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.model.Copia;
import com.example.libreria.service.CopiaService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copie")
public class CopiaController {
    private final CopiaService copiaService;

    public CopiaController(CopiaService copiaService) {
        this.copiaService = copiaService;
    }

    @GetMapping("/all")
    public List<CopiaDTO> getAll(){
        return copiaService.getAll();
    }
    @GetMapping("/allbynumcopie/{num_copie}")
    public ResponseEntity<?> getAllByNumCopie(@PathVariable Integer num_copie){
        try{
            return ResponseEntity.ok(copiaService.getAllByNumeroCopie(num_copie));
        } catch (CopiaNotFoundException e){
            // Dato che il metodo
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La copia non esiste.");
        }
    }
    /**
     *
     */
    @GetMapping("/allbycasaeditrice/{id}")
    public ResponseEntity<?> getAllByCasaEditrice(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(copiaService.getAllByCasaEditrice(id));
        } catch (CasaEditriceNotFound | CopiaNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getbyisbn/{isbn}")
    public ResponseEntity<?> getByLibro(@PathVariable Integer isbn){
        try{
            return ResponseEntity.ok(copiaService.getByIsbn(isbn));
        } catch(CopiaNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/disponibili")
    public ResponseEntity<?> getByNumeroCopieDisponibili(){
        try{
            return ResponseEntity.ok(copiaService.getByNumeroCopieDisponibili());
        } catch (CopiaNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/esaurite")
    public ResponseEntity<?> getByNumeroCopieEsaurite(){
        try {
            return ResponseEntity.ok(copiaService.getByNumeroCopieEsaurite());
        } catch (CopiaNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<?> addCopia(@RequestBody CopiaRequest copiaRequest){
        try{
            return ResponseEntity.ok(copiaService.addCopia(copiaRequest));
        } catch(LibroNotFoundException | CasaEditriceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // PUT
    @PutMapping("/{isbn}")
//    public ResponseEntity<String> modificaCopia(@PathVariable Integer isbn,Integer idCasaEditrice,Integer numeroCopie){
//        // Posso usare lo stesso servizio poichè la save quando vede che la copia già esiste nel DB ne fa una UPDATE.
//
//    }
    // DELETE
    @DeleteMapping("/{isbn}")
    public void deleteCopia(@PathVariable Integer isbn){
        copiaService.deleteCopia(isbn);
    }

}
