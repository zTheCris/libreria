package com.example.libreria.controller;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.dto.LibroDTO;
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
    public ResponseEntity<List<CopiaDTO>> getAllByNumCopie(@PathVariable Integer num_copie){
        return ResponseEntity.ok(copiaService.getAllByNumeroCopie(num_copie));
    }
    /**
     *
     */
    @GetMapping("/allbycasaeditrice/{id}")
    public ResponseEntity<List<CopiaDTO>> getAllByCasaEditrice(@PathVariable Integer id){
        return ResponseEntity.ok(copiaService.getAllByCasaEditrice(id));
    }
    @GetMapping("/getbyisbn/{isbn}")
    public ResponseEntity<CopiaDTO> getByLibro(@PathVariable Integer isbn){
        return ResponseEntity.ok(copiaService.getByIsbn(isbn));
    }

    @GetMapping("/disponibili")
    public ResponseEntity<List<CopiaDTO>> getByNumeroCopieDisponibili(){
        return ResponseEntity.ok(copiaService.getByNumeroCopieDisponibili());
    }
    @GetMapping("/esaurite")
    public ResponseEntity<List<CopiaDTO>> getByNumeroCopieEsaurite(){
       return ResponseEntity.ok(copiaService.getByNumeroCopieEsaurite());
    }

    // POST
    @PostMapping
    public ResponseEntity<?> addCopia(@RequestBody CopiaRequest copiaRequest){
      return ResponseEntity.ok(copiaService.addCopia(copiaRequest));
    }
    // PUT
    @PutMapping("/{isbn}")
//    public ResponseEntity<String> modificaCopia(@PathVariable Integer isbn,Integer idCasaEditrice,Integer numeroCopie){
//        // Posso usare lo stesso servizio poichè la save quando vede che la copia già esiste nel DB ne fa una UPDATE.
//
//    }
    // DELETE
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteCopia(@PathVariable Integer isbn){
        copiaService.deleteCopia(isbn);
        return ResponseEntity.ok("Copia eliminata correttamente.");
    }

}
