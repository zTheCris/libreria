package com.example.libreria.utility;

import com.example.libreria.dto.GenereDTO;
import com.example.libreria.dto.LibroDTO;
import com.example.libreria.model.Genere;
import com.example.libreria.model.Libro;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Mapper(){}

    public LibroDTO convertLibroToLibroDTO(Libro libroDaConv){
        LibroDTO libroDTOret = new LibroDTO();
        libroDTOret.setIsbn(libroDaConv.getIsbn());
        libroDTOret.setTitolo(libroDaConv.getTitolo());
        libroDTOret.setGenere(convertGenereToGenereDTO(libroDaConv.getGenere()));
        return libroDTOret;
    }

    public Libro convertLibroDTOToLibro(LibroDTO libroDTOdaConv){
        Libro libroRet = new Libro();
        libroRet.setTitolo(libroDTOdaConv.getTitolo());
        libroRet.setAnno(libroDTOdaConv.getAnno());
        libroRet.setGenere(convertGenereDTOToGenere(libroDTOdaConv.getGenere()));
        return libroRet;
    }
    //
    private Genere convertGenereDTOToGenere(GenereDTO genereDTO) {
        Genere genereRet = new Genere();
        genereRet.setNome(genereDTO.getNome());
        return genereRet;
    }

    private GenereDTO convertGenereToGenereDTO(Genere genere) {
        GenereDTO genereDTOret = new GenereDTO();
        genereDTOret.setIdGenere(genere.getIdGenere());
        genereDTOret.setNome(genere.getNome());
        return genereDTOret;
    }
}
