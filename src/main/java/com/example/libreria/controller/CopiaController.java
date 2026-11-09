package com.example.libreria.controller;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.service.CopieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copie")
public class CopiaController {
    private final CopieService copieService;

    public CopiaController(CopieService copieService) {
        this.copieService = copieService;
    }

    @GetMapping("/all")
    public List<CopiaDTO> getAll(){
        return copieService.getAll();
    }
    @GetMapping("/allbynumcopie/{num_copie}")
    public List<CopiaDTO> getAllByNumCopie(@PathVariable Integer num_copie){
        return copieService.getAllByNumeroCopie(num_copie);
    }
    /**
     *
     */
    @GetMapping("/allbycasaeditrice/{id}")
    public List<CopiaDTO> getAllByCasaEditrice(@PathVariable Integer id){
        return copieService.getAllByCasaEditrice(id);
    }
    @GetMapping("/getbyisbn/{isbn}")
    public CopiaDTO getByLibro(@PathVariable Integer isbn){
        return copieService.getByIsbn(isbn);
    }

    @GetMapping("/disponibili")
    public List<CopiaDTO> getByNumeroCopieDisponibili(){
        return copieService.getByNumeroCopieDisponibili();
    }
    @GetMapping("/esaurite")
    public List<CopiaDTO> getByNumeroCopieEsaurite(){
        return copieService.getByNumeroCopieEsaurite();
    }

    // POST
    @PostMapping
    public CopiaDTO addCopia(Integer isbn,Integer idCasaEditrice,Integer numeroCopie){
        return copieService.addCopia(isbn,idCasaEditrice,numeroCopie);
    }
    // PUT
    @PutMapping("/{isbn}")
    public CopiaDTO modificaCopia(Integer isbn,Integer idCasaEditrice,Integer numeroCopie){
        // Posso usare lo stesso servizio poichè la save quando vede che la copia già esiste nel DB ne fa una UPDATE.
        return copieService.addCopia(isbn,idCasaEditrice,numeroCopie);
    }
    // DELETE
    @DeleteMapping("/{isbn}")
    public void deleteCopia(Integer isbn){
        copieService.deleteCopia(isbn);
    }

}
