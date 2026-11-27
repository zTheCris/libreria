package com.example.libreria.controller;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.dto.StringDTO;
import com.example.libreria.dto.request.CopiaRequest;
import com.example.libreria.service.CopiaService;
import jakarta.validation.Valid;
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

    @GetMapping()
    public ResponseEntity<List<CopiaDTO>> getAll(){
        return ResponseEntity.ok(copiaService.getAll());
    }

    @GetMapping("/num")
    public ResponseEntity<Long> getNumLibri(){
        return ResponseEntity.ok(copiaService.getNumCopie());
    }

    @GetMapping("/allbynumcopie/{num_copie}")
    public ResponseEntity<List<CopiaDTO>> getAllByNumCopie(@PathVariable Integer num_copie){
        return ResponseEntity.ok(copiaService.getAllByNumeroCopie(num_copie));
    }

    @GetMapping("/allbycasaeditrice/{id}")
    public ResponseEntity<List<CopiaDTO>> getAllByCasaEditrice(@PathVariable Integer id){
        return ResponseEntity.ok(copiaService.getAllByCasaEditrice(id));
    }

    @GetMapping("/getbyisbn/{isbn}")
    public ResponseEntity<List<CopiaDTO>> getByLibro(@PathVariable Integer isbn){
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
    public ResponseEntity<CopiaDTO> addCopia(@Valid @RequestBody CopiaRequest copiaRequest){
      return ResponseEntity.ok(copiaService.addCopia(copiaRequest));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<CopiaDTO> modificaCopia(@PathVariable Integer id,@Valid @RequestBody CopiaRequest copiaRequest){
        // Posso usare lo stesso servizio poichè la save quando vede che la copia già esiste nel DB ne fa una UPDATE.
        return ResponseEntity.ok(copiaService.modificaCopia(id,copiaRequest));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<StringDTO> deleteCopia(@PathVariable Integer isbn){
        copiaService.deleteCopia(isbn);
        return ResponseEntity.ok(new StringDTO("Copia eliminata correttamente."));
    }

}
