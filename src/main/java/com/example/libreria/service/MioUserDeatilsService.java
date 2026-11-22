package com.example.libreria.service;

import com.example.libreria.exception.UtenteNotFoundException;
import com.example.libreria.model.Utente;
import com.example.libreria.repository.UtenteRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MioUserDeatilsService implements UserDetailsService {
    // Inietto il repository
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public MioUserDeatilsService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Nel mio caso, lo username è l'email.
     * @param email the email identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recupero l'utente tramite email
        Utente utente = utenteRepository.findByEmail(email).orElseThrow(()-> new UtenteNotFoundException("Utente non trovato."));
        // Se lho trovato, devo crearmi l'oggetto UserDetails con quei parametri.
        UserDetails userDetails = User.builder()
                .username(utente.getEmail())
                .password(utente.getPassword())
                .roles("USER")
                .build();
        // Lo rimando al chiamante.
        return userDetails;
    }
}
