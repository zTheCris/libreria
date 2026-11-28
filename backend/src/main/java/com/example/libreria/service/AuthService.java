package com.example.libreria.service;

import com.example.libreria.dto.RegistraUtenteDTO;
import com.example.libreria.dto.request.RegistraUtenteRequest;
import com.example.libreria.dto.request.UtenteRequest;

import com.example.libreria.exception.UtenteAlreadyExistsException;
import com.example.libreria.model.Utente;
import com.example.libreria.repository.UtenteRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public String autenticazione(UtenteRequest utenteRequest){
        // Dico all'AuthenticationManager che l'autenticazione avviene tramite Username e Password.
        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteRequest.getUsername(), utenteRequest.getPassword());

        // Autentico l'utente tramite l'AutenticationManager
        authentication = authenticationManager.authenticate(authentication);

        // Passo la responsabilità della generazione del token a JWT. Se il metodo torna una Stringa, il token è stato generato.
        return jwtService.generaToken(authentication);

    }

    public String register(RegistraUtenteRequest registraUtenteRequest) {
        // Quando registro l'utente, gli devo rimandare il token Jwt da subito.

        // Devo creare un nuovo utente del database. Prima però, controllo se già esiste data l'email.
        if (utenteRepository.findByEmail(registraUtenteRequest.getEmail()).isPresent()){
            throw new UtenteAlreadyExistsException("L'email è già in uso.");
        }
        // Adesso però, priam di creare l'utente, devo criptare la password. Uso il passwordEncoder.
        String passwordCodificata = passwordEncoder.encode(registraUtenteRequest.getPassword());

        // Ora creo l'utente con quello che ha mandato il client.
        Utente utente = new Utente(registraUtenteRequest.getEmail(),passwordCodificata,registraUtenteRequest.getCognome(),registraUtenteRequest.getNome());

        // Lo salvo nel db
        utenteRepository.save(utente);

        // Adesso devo generare il token con jwt, dato che l'authentication manager ora è il Dao, che è collegato con il DB.
        // Tuttavia, devo cerare l'oggetto autenticazione.
        Authentication authentication = new UsernamePasswordAuthenticationToken(registraUtenteRequest.getEmail(), registraUtenteRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);

        // Autentico l'utente tramite l'AutenticationManager


        return jwtService.generaToken(authentication);
    }
    // Il prossimo layer è il JwtService.
}
