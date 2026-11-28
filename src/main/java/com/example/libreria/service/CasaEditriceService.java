package com.example.libreria.service;

import com.example.libreria.dto.CasaEditriceDTO;
import com.example.libreria.dto.request.CasaEditriceRequest;
import com.example.libreria.exception.CasaEditriceAlreadyExists;
import com.example.libreria.exception.CasaEditriceNotFound;
import com.example.libreria.model.CasaEditrice;
import com.example.libreria.model.Copia;
import com.example.libreria.repository.CasaEditriceRepository;
import com.example.libreria.repository.CopiaRepository;
import com.example.libreria.utility.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaEditriceService {
    private final CasaEditriceRepository casaEditriceRepository;
    private final Mapper mapper;
    private final CopiaRepository copiaRepository;

    public CasaEditriceService(CasaEditriceRepository casaEditriceRepository, Mapper mapper, CopiaRepository copiaRepository) {
        this.casaEditriceRepository = casaEditriceRepository;
        this.mapper = mapper;
        this.copiaRepository = copiaRepository;
    }
    // Provo a farlo con le stream cosi da non ripetere il codice.
    public List<CasaEditriceDTO> getAll(){
        return casaEditriceRepository.findAll()
                .stream()
                .map(mapper::convertCasaEditriceToCasaEditriceDTO)
                .toList();
    }
    public CasaEditriceDTO getByNome(String nome){
        return mapper.convertCasaEditriceToCasaEditriceDTO(casaEditriceRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(()-> new CasaEditriceNotFound("Casa editrice non trovata.")));
    }

    public Long getNumCase(){
        return casaEditriceRepository.count();
    }

    @Transactional
    public CasaEditriceDTO addCasa(CasaEditriceRequest casaEditriceRequest) {
        if(casaEditriceRepository.findByNomeIgnoreCase(casaEditriceRequest.getNome()).isPresent())
            throw new CasaEditriceAlreadyExists("La casa editrice già esiste.");
        return mapper.convertCasaEditriceToCasaEditriceDTO(casaEditriceRepository.save(new CasaEditrice(casaEditriceRequest.getNome())));
    }
    @Transactional
    public CasaEditriceDTO modificaCasa(Integer id,CasaEditriceRequest casaEditriceRequest) {
        CasaEditrice casaEditrice = casaEditriceRepository.findById(id).orElseThrow(() -> new CasaEditriceNotFound("Casa editrice non trovata."));
        casaEditrice.setNome(casaEditriceRequest.getNome());
        return mapper.convertCasaEditriceToCasaEditriceDTO(casaEditriceRepository.save(casaEditrice));
    }

    @Transactional
    public boolean delete(Integer id) {
        // Mi ricavo la casa editrice perché se devo cancellarla, devo settare a null tutte i record di Copie dove è presente la casa editrice.
        CasaEditrice casaEditrice = casaEditriceRepository.findById(id).orElseThrow(()-> new CasaEditriceNotFound("Casa editrice non trovata."));
        List<Copia> copieConCasa = copiaRepository.findAllByCasaEditrice(casaEditrice);
        // Per ogni Copia, setto la casa editrice a null.
        // Per scrivere una riga di codice in meno uso il metodo forEach degli Iterables, poichè una Collection implementa Iterables.
        copieConCasa.forEach(copia -> copia.setCasaEditrice(null));
        // Grazie ad Hibernate, non serve nemmeno fare la saveAll perché basta che eseguo il Setter della classe e lui esegue una INSERT
        casaEditriceRepository.delete(casaEditrice);
        return true;
    }
}
