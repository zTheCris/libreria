package com.example.libreria.utility;

import com.example.libreria.dto.*;
import com.example.libreria.model.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility per convertire correttamente {@code Entity } <──> {@code DTO}.
 */
@Component
public class Mapper {

    public Mapper(){}

    // Libro

    public LibroDTO convertLibroToLibroDTO(Libro libroDaConv){
        LibroDTO libroDTOret = new LibroDTO();
        libroDTOret.setIsbn(libroDaConv.getIsbn());
        libroDTOret.setTitolo(libroDaConv.getTitolo());
        libroDTOret.setAnno(libroDaConv.getAnno());
        libroDTOret.setGenere(convertGenereToGenereDTO(libroDaConv.getGenere()));
        // Ho scelto di avere il Set di autori solamente in Libri, e non viceversa.
        // Guardare LibroDTO per la spiegazione.
        for (Autore autore:libroDaConv.getAutori())
            // Uso il getter del Set per poter aggiungere un autore alla lista.
            // Altrimenti, avrei potuto creare un Set<AutoreDTO> in questo metodo
            // Per poi fare il set
            libroDTOret.getAutoriDTO().add(convertAutoreToAutoreDTO(autore));
        return libroDTOret;
    }

    public Libro convertLibroDTOToLibro(LibroDTO libroDTOdaConv){
        Libro libroRet = new Libro();
        libroRet.setTitolo(libroDTOdaConv.getTitolo());
        libroRet.setAnno(libroDTOdaConv.getAnno());
        libroRet.setGenere(convertGenereDTOToGenere(libroDTOdaConv.getGenere()));

        // PER L'INSERIMENTO DI LIBRI AUTORI, SE NE OCCUPA IL SERVICE.
        return libroRet;
    }

    //Genere

    public Genere convertGenereDTOToGenere(GenereDTO genereDTO) {
        Genere genereRet = new Genere();
        genereRet.setNome(genereDTO.getNome());
        return genereRet;
    }

    public GenereDTO convertGenereToGenereDTO(Genere genere) {
        GenereDTO genereDTOret = new GenereDTO();
        genereDTOret.setIdGenere(genere.getIdGenere());
        genereDTOret.setNome(genere.getNome());
        return genereDTOret;
    }

    // Autore

    public AutoreDTO convertAutoreToAutoreDTO(Autore autore){
        AutoreDTO autoreDTOret = new AutoreDTO();
        autoreDTOret.setIdAutore(autore.getIdAutore());
        autoreDTOret.setNome(autore.getNome());
        autoreDTOret.setCognome(autore.getCognome());
        autoreDTOret.setAnno(autore.getData());
        autoreDTOret.setNazionalitaDTO(convertNazionalitaToNazionalitaDTO(autore.getNazionalita()));


        return autoreDTOret;
    }
    public Autore convertAutoreDTOToAutore(AutoreDTO autoreDTO){
        Autore autoreRet = new Autore();
        autoreRet.setNome(autoreDTO.getNome());
        autoreRet.setCognome(autoreDTO.getCognome());
        autoreRet.setData(autoreDTO.getData());
        autoreRet.setNazionalita(convertNazionalitaDTOToNazionalita(autoreDTO.getNazionalitaDTO()));
        // PER L'INSERIMENTO DI LIBRI AUTORI, SE NE OCCUPA IL SERVICE.
        return autoreRet;
    }

    // Nazionalita

    public Nazionalita convertNazionalitaDTOToNazionalita(NazionalitaDTO nazionalitaDTO) {
        Nazionalita nazionalitaRet = new Nazionalita();
        nazionalitaRet.setNome(nazionalitaDTO.getNome());
        return nazionalitaRet;
    }


    public NazionalitaDTO convertNazionalitaToNazionalitaDTO(Nazionalita nazionalita) {
        NazionalitaDTO nazionalitaDTOret = new NazionalitaDTO();
        nazionalitaDTOret.setId_nazionalita(nazionalita.getId_nazionalita());
        nazionalitaDTOret.setNome(nazionalita.getNome());
        return nazionalitaDTOret;
    }

    // CasaEditrice
    public CasaEditriceDTO convertCasaEditriceToCasaEditriceDTO(CasaEditrice casaEditrice){
        CasaEditriceDTO casaEditriceDTOret = new CasaEditriceDTO();
        casaEditriceDTOret.setId_casaeditrice(casaEditrice.getId_casaeditrice());
        casaEditriceDTOret.setNome(casaEditrice.getNome());
        return casaEditriceDTOret;
    }
    public CasaEditrice convertCasaEditriceDTOToCasaEditrice(CasaEditriceDTO casaEditriceDTO){
        CasaEditrice casaEditriceRet = new CasaEditrice();
        casaEditriceRet.setNome(casaEditriceDTO.getNome());
        return casaEditriceRet;
    }
    // Copie
    public CopiaDTO convertCopieToCopieDTO(Copia copia){
        CopiaDTO copiaDTOret = new CopiaDTO();
        copiaDTOret.setCasaEditriceDTO(convertCasaEditriceToCasaEditriceDTO(copia.getCasaEditrice()));
        copiaDTOret.setIdCopia(copia.getIdCopia());
        copiaDTOret.setLibroDTO(convertLibroToLibroDTO(copia.getLibro()));
        copiaDTOret.setNumero_copie(copia.getNumero_copie());
        return copiaDTOret;
    }

    // DA AGGIUNGERE: CONVERTITORE DA LISTA ENTITY A LISTA DTO USANDO LA REFLECTION.

    // DA AGGIUNGERE (dopo aver approfondito la teoria): CONVERTITORE LISTA ENTITY IN LISTA DTO USANDO COME PARAMENTRO LA FUNZIONE CONVERTITRICE SCRITTA SOPRA.

}
