package com.example.libreria.security;

import com.example.libreria.service.JwtService;
import com.example.libreria.service.MioUserDeatilsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Inietterà anch'esso JwtService
    private final JwtService jwtService;
    private final MioUserDeatilsService miouserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, MioUserDeatilsService mioUserDeatilsService) {
        this.jwtService = jwtService;
        this.miouserDetailsService = mioUserDeatilsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Filtro su ognuno dei 3 oggetti.

        // Se non lo metto angular non mi funziona con la configurazione attuale.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // getHeader vuole sapere su quale stringa vuole operare, la stringa compketa inizia con Authentication ..."
        String authHeader = request.getHeader("Authorization");
        // Ora devo vedere se la stringa inizia con Bearer .
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Dato che l'autorizzazione non è valida, passo la responsabiltà al chain successivo.
            filterChain.doFilter(request, response);
            return;
        }
        // Se l'Header è valido, estraggo il token Jwt completo dall'authHeader. Dato che la stringa inizia con "Bearer ",
        // Anadiamo dal settimo carattere in poi.

        final String jwtDaHeader = authHeader.substring(7);
        // Ora verifico se il JWT è valido, prima di continuare.
        if (jwtService.validaToken(jwtDaHeader)) {

            // Estraggo lo Username usando il metodo nel service Jwt
            final String usernameDaHeader = jwtService.estraiUsername(jwtDaHeader);

            // Adesso devo controllare se lo username è stato convalidato! Allo stesso tempo, controllo se l'utente non è stato autenticato.

            if (usernameDaHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Se siamo qui dentro, vuol dire che il service ha estratto l'utente e l'utente non è stato ancora autenticato.
                // Quindi, autentichiamo l'utente come abbiamo fatto in SpringSecurityConfig.
                // PRIMA PERO: verifichiamo il token.


                UserDetails userDetails = miouserDetailsService.loadUserByUsername(usernameDaHeader);
                UsernamePasswordAuthenticationToken tokenAutorizzato = new UsernamePasswordAuthenticationToken(
                        usernameDaHeader,
                        null,
                        userDetails.getAuthorities()
                );

                // Se ho fatto tutto e ho creato il tokenAutorizzato, dico a Spring Security che è autorizzato, e passo al chain successivo
                SecurityContextHolder.getContext().setAuthentication(tokenAutorizzato);


            }
            // Passo la responsabilità al chain successivo.
            filterChain.doFilter(request, response);

        } else {
            // Se entra qui, il valida token ha restituito fasle. Qui manderò il 401 tramite l'oggetto HttpResponse della Servlet.
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Il token non è valido.");

        }
    }


}
