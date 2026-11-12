package com.example.libreria.service;

import com.example.libreria.dto.CopiaDTO;
import com.example.libreria.dto.request.CopiaRequest;
import com.example.libreria.exception.CasaEditriceNotFound;
import com.example.libreria.exception.CopiaNotFoundException;
import com.example.libreria.exception.LibroNotFoundException;
import com.example.libreria.model.CasaEditrice;
import com.example.libreria.model.Copia;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.CasaEditriceRepository;
import com.example.libreria.repository.CopiaRepository;
import com.example.libreria.repository.LibroRepository;
import com.example.libreria.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CopiaService {
    private final CopiaRepository copiaRepository;
    private final Mapper mapper;
    private final CasaEditriceRepository casaEditriceRepository;
    private final LibroRepository libroRepository;

    // Constructor Injection
    public CopiaService(CopiaRepository copiaRepository, Mapper mapper, CasaEditriceRepository casaEditriceRepository, LibroRepository libroRepository) {
        this.copiaRepository = copiaRepository;
        this.mapper = mapper;
        this.casaEditriceRepository = casaEditriceRepository;
        this.libroRepository = libroRepository;
    }


    // Metodi
    public List<CopiaDTO> getAll(){
        List<Copia> rispostaDB = copiaRepository.findAll();
        List<CopiaDTO> copieDTOret = new ArrayList<>();
        for (Copia copia:rispostaDB)
            copieDTOret.add(mapper.convertCopiaToCopieDTO(copia));
        return copieDTOret;
    }

    public List<CopiaDTO> getAllByNumeroCopie(Integer numeroCopie){
        List<Copia> rispostaDB = copiaRepository.findByNumeroCopie(numeroCopie);
        if(rispostaDB.isEmpty()) throw new CopiaNotFoundException("Non sono presenti Copie con numero di copie "+numeroCopie);
        List<CopiaDTO> copieDTOret = new ArrayList<>();
        for (Copia copia:rispostaDB)
            copieDTOret.add(mapper.convertCopiaToCopieDTO(copia));
        return copieDTOret;
    }


    public List<CopiaDTO> getAllByCasaEditrice(Integer id) {
        CasaEditrice casaEditrice = casaEditriceRepository.findById(id).orElseThrow(()-> new CasaEditriceNotFound("Casa editrice non trovata"));
        List<Copia> rispostaDB = copiaRepository.findAllByCasaEditrice(casaEditrice);
        List<CopiaDTO> copieDTOret = new ArrayList<>();
        for (Copia copia:rispostaDB)
            copieDTOret.add(mapper.convertCopiaToCopieDTO(copia));
        return copieDTOret;
    }

    public CopiaDTO getByIsbn(Integer isbn) {
        Libro libro = libroRepository.findById(isbn).orElseThrow(()-> new LibroNotFoundException("Libro non trovato."));
        return mapper.convertCopiaToCopieDTO(copiaRepository.findAllByLibro(libro));
    }
    // Aggiungi copia
    public CopiaDTO addCopia(CopiaRequest copiaRequest) {
        // Controllo se il libro esiste
        Libro libro = libroRepository.findById(copiaRequest.getIsbn()).orElseThrow(()-> new LibroNotFoundException("Libro non trovato."));
        // Per la casa editrice, se non esiste nel DB la inserisco.

        CasaEditrice casaEditrice = casaEditriceRepository.findByNome(copiaRequest.getCasaEditrice())
                .orElseGet(
                        ()->addCasaEditrice(copiaRequest.getCasaEditrice())
                );
        // Ora creo la copia
        Copia copia = new Copia(copiaRequest.getNumeroCopie(),libro,casaEditrice);
        return mapper.convertCopiaToCopieDTO(copiaRepository.save(copia));
     }
     public CopiaDTO modificaCopia(Integer idCopia, CopiaRequest copiaRequest){
        // Controllo se la copia esiste
         Copia copia = copiaRepository.findById(idCopia).orElseThrow(()-> new CopiaNotFoundException("Copia non trovata."));

         // Controllo se il libro esiste
         Libro libro = libroRepository.findById(copiaRequest.getIsbn()).orElseThrow(()-> new LibroNotFoundException("Libro non trovato."));

         // Controllo se la nuova casa editrice esiste.
         CasaEditrice casaEditrice = casaEditriceRepository.findByNome(copiaRequest.getCasaEditrice())
                 .orElseGet(
                         ()->addCasaEditrice(copiaRequest.getCasaEditrice())
                 );
         // Ora creo la copia
         copia.setNumeroCopie(copiaRequest.getNumeroCopie());
         copia.setLibro(libro);
         copia.setCasaEditrice(casaEditrice);
         return mapper.convertCopiaToCopieDTO(copiaRepository.save(copia));
     }
        

    public void deleteCopia(Integer idCopia) {
        copiaRepository.deleteById(idCopia);
    }

    public List<CopiaDTO> getByNumeroCopieDisponibili() {
        List<Copia> rispostaDB = copiaRepository
                .findAll()
                .stream()
                // Ora filtro per le copie maggiori di 0
                .filter(copia->copia.getNumeroCopie()>0)
                // Converto in lista
                .toList();
        if(rispostaDB.isEmpty()) throw new CopiaNotFoundException("Non ci sono copie disponibili");
        List<CopiaDTO> copieDTOret = new ArrayList<>();
        for (Copia copia:rispostaDB)
            copieDTOret.add(mapper.convertCopiaToCopieDTO(copia));
        return copieDTOret;

    }
    public List<CopiaDTO> getByNumeroCopieEsaurite() {
        // Posso direttamente usare il getAllByNumeroCopie cosi da non riutilizzare il codice.
        return getAllByNumeroCopie(0);

    }
    //Metodi
    private CasaEditrice addCasaEditrice(String casaEditrice){
        return casaEditriceRepository.save(new CasaEditrice(casaEditrice));
    }

}
