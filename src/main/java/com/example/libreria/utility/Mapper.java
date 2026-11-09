package com.example.libreria.utility;

import com.example.libreria.dto.*;
import com.example.libreria.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Utility per convertire correttamente {@code Entity } <──> {@code DTO}.
 */
@Component
public class Mapper {

    public Mapper() {
    }

    // Libro

    public LibroDTO convertLibroToLibroDTO(Libro libroDaConv) {
        LibroDTO libroDTOret = new LibroDTO();
        libroDTOret.setIsbn(libroDaConv.getIsbn());
        libroDTOret.setTitolo(libroDaConv.getTitolo());
        libroDTOret.setAnno(libroDaConv.getAnno());
        libroDTOret.setGenere(convertGenereToGenereDTO(libroDaConv.getGenere()));
        libroDTOret.setPrezzo(libroDaConv.getPrezzo());
        // Ho scelto di avere il Set di autori solamente in Libri, e non viceversa.
        // Guardare LibroDTO per la spiegazione.
        for (Autore autore : libroDaConv.getAutori())
            // Uso il getter del Set per poter aggiungere un autore alla lista.
            // Altrimenti, avrei potuto creare un Set<AutoreDTO> in questo metodo
            // Per poi fare il set
            libroDTOret.getAutori().add(convertAutoreToAutoreDTO(autore));
        return libroDTOret;
    }

    public Libro convertLibroDTOToLibro(LibroDTO libroDTOdaConv) {
        Libro libroRet = new Libro();
        libroRet.setTitolo(libroDTOdaConv.getTitolo());
        libroRet.setAnno(libroDTOdaConv.getAnno());
        libroRet.setGenere(convertGenereDTOToGenere(libroDTOdaConv.getGenere()));
        libroRet.setPrezzo(libroDTOdaConv.getPrezzo());
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

    public AutoreDTO convertAutoreToAutoreDTO(Autore autore) {
        AutoreDTO autoreDTOret = new AutoreDTO();
        autoreDTOret.setIdAutore(autore.getIdAutore());
        autoreDTOret.setNome(autore.getNome());
        autoreDTOret.setCognome(autore.getCognome());
        autoreDTOret.setData(autore.getData());
        autoreDTOret.setNazionalita(convertNazionalitaToNazionalitaDTO(autore.getNazionalita()));

        return autoreDTOret;
    }

    public Autore convertAutoreDTOToAutore(AutoreDTO autoreDTO) {
        Autore autoreRet = new Autore();
        autoreRet.setNome(autoreDTO.getNome());
        autoreRet.setCognome(autoreDTO.getCognome());
        autoreRet.setData(autoreDTO.getData());
        autoreRet.setNazionalita(convertNazionalitaDTOToNazionalita(autoreDTO.getNazionalita()));
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
        nazionalitaDTOret.setIdNazionalita(nazionalita.getIdNazionalita()   );
        nazionalitaDTOret.setNome(nazionalita.getNome());
        return nazionalitaDTOret;
    }

    // CasaEditrice
    public CasaEditriceDTO convertCasaEditriceToCasaEditriceDTO(CasaEditrice casaEditrice) {
        CasaEditriceDTO casaEditriceDTOret = new CasaEditriceDTO();
        casaEditriceDTOret.setId_casaeditrice(casaEditrice.getId_casaeditrice());
        casaEditriceDTOret.setNome(casaEditrice.getNome());
        return casaEditriceDTOret;
    }

    public CasaEditrice convertCasaEditriceDTOToCasaEditrice(CasaEditriceDTO casaEditriceDTO) {
        CasaEditrice casaEditriceRet = new CasaEditrice();
        casaEditriceRet.setNome(casaEditriceDTO.getNome());
        return casaEditriceRet;
    }

    // Copie
    public CopiaDTO convertCopieToCopieDTO(Copia copia) {
        CopiaDTO copiaDTOret = new CopiaDTO();
        copiaDTOret.setCasaEditrice(convertCasaEditriceToCasaEditriceDTO(copia.getCasaEditrice()));
        copiaDTOret.setIdCopia(copia.getIdCopia());
        copiaDTOret.setLibro(convertLibroToLibroDTO(copia.getLibro()));
        copiaDTOret.setNumeroCopie(copia.getNumeroCopie());
        return copiaDTOret;
    }
    public Copia convertCopiaDTOToCopia(CopiaDTO copiaDTO) {
       Copia copiaRet = new Copia();
       copiaRet.setLibro(convertLibroDTOToLibro(copiaDTO.getLibro()));
       copiaRet.setCasaEditrice(convertCasaEditriceDTOToCasaEditrice(copiaDTO.getCasaEditrice()));
       copiaRet.setNumeroCopie(copiaDTO.getNumeroCopie());
       return copiaRet;
    }

    // DA AGGIUNGERE: CONVERTITORE DA LISTA ENTITY A LISTA DTO USANDO SOLO LA REFLECTION.


    // --------------------------------

    // DA USARE SOLO DOPO AVER APPROFONDITO LA TEORIA): CONVERTITORE LISTA ENTITY IN LISTA DTO USANDO COME PARAMENTRO LA FUNZIONE CONVERTITRICE SCRITTA SOPRA.
    // DA USARE SOLO DOPO AVER APPROFONDITO LA TEORIA): CONVERTITORE LISTA ENTITY IN LISTA DTO USANDO COME PARAMENTRO LA FUNZIONE CONVERTITRICE SCRITTA SOPRA.
    // DA USARE SOLO DOPO AVER APPROFONDITO LA TEORIA): CONVERTITORE LISTA ENTITY IN LISTA DTO USANDO COME PARAMENTRO LA FUNZIONE CONVERTITRICE SCRITTA SOPRA.
    // DA USARE SOLO DOPO AVER APPROFONDITO LA TEORIA): CONVERTITORE LISTA ENTITY IN LISTA DTO USANDO COME PARAMENTRO LA FUNZIONE CONVERTITRICE SCRITTA SOPRA.

    /**
     * In Java esistono i tipi Generics, utilizzati per definire classi e metodi
     * che possono lavorare con tipi differenti.
     * <br>
     * È possibile passare una funzione come parametro di un metodo
     * attraverso l'interfaccia {@code Function}, disponibile da Java 8.
     * <br>
     * Il metodo riceve una lista di elementi di tipo E e una funzione
     * che accetta un elemento di tipo E e restituisce un elemento di tipo D.
     * <br>
     * Il valore restituito dalla funzione viene aggiunto alla lista
     * di ritorno listaDTO.
     * <br>
     * Il tipo E indica la classe Entity, mentre D indica la classe DTO.
     *
     * @param listaEntity lista delle Entity da convertire
     * @param converter funzione che converte una Entity in un DTO
     * @param <E> tipo della Entity
     * @param <D> tipo del DTO
     * @return lista contenente i DTO convertiti
     */
    public <E, D> List<D> convertiLista(List<E> listaEntity, Function<E, D> converter) {
        List<D> listaDTO = new ArrayList<>();
        for (E entita : listaEntity)
            listaDTO.add(converter.apply(entita));
        return listaDTO;
    }

}
