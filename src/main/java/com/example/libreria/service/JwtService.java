package com.example.libreria.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    // La chiave va a leggerla dall'application.properties che è collegato al .env
    public JwtService(@Value("${jwt.secret}")String chiaveDaEnv) {
        secretKey = Keys.hmacShaKeyFor(chiaveDaEnv.getBytes());
    }
// Qui dentro generiamo il token JWT.


    // Corrisponde a 15 minuti. In millisecondi, quindi 15*60*1000 = 900000
    private final Integer SCADENZA_TOKEN = 86400000;
    // 24 ORE: 86400000

    public String generaToken(Authentication authentication){
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+SCADENZA_TOKEN))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Passaggi:
     * 1) Verifico se il token passato è valido
     * 2) Se è valido, costruisco l'oggetto Parser che può interpretare il token da Stringa.
     * 3) Provo a passare i Claims (il contenuto del token)
     * 4) Dai Claims, mi prendo tutto il Payload
     * 5) Ritorno al chiamante l'username di chi ha richiesto il token (subject)
     * @param token
     * @return lo username. In contemporanea, verifica che il token è valido.
     */
    public String estraiUsername(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validaToken(String token){
        // Dato che il metodo sopra estrae l'username SOLO se il token è valido, basta verificare che il metodo restituisca
        // effettivamente una stringa.
          try{
              estraiUsername(token);
              return true;
          } catch (JwtException | IllegalArgumentException e){
              // Se entro qui dentro significa che non è riuscito a parsare il token.
              return false;
          }
    }
}
