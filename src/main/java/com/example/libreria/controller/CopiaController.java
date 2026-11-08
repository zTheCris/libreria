package com.example.libreria.controller;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.repository.CopiaRepository;
import com.example.libreria.service.CopieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
