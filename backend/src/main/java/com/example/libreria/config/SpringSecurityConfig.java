package com.example.libreria.config;


import com.example.libreria.security.JwtAuthenticationFilter;
import com.example.libreria.service.JwtService;
import com.example.libreria.service.MioUserDeatilsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * La configurazione prevede l'autenticazione attraverso {@code formLogin}. Il form di base è configurato da Spring Security e vive sulla
 *  <a href="http://localhost:8080/login">pagina di Login default </a> di Spring.
 *  Inoltre, c'è da sapere che Spring Security manda in maniera nascosta un {@code cookie} che contiene {@code "JSESSIONID"}.
 *  In questo modo, riesce a riconoscere il Client che si è autenticato.
 *
 */
@Configuration
public class SpringSecurityConfig {
    // E' necessario abilitare il server di Angular.
    // Il modo per farlo, è creare un bean di tipo CorsConfigurationSource e passarlo al filterChain.
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
    //  Quali origini permetto?
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Quali metodi HTTP?
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));

        // Nell'header della richiesta, cosa permetto che mi arriva?
        corsConfiguration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        // Di default, l'autorizzazione avviene tramite Cookie, quindi la disabilito
        corsConfiguration.setAllowCredentials(false);

        // Ora ritorno la Source con il cors configurato Sarà di tipo UrlBasedConfigurationSource
        UrlBasedCorsConfigurationSource sorgente = new UrlBasedCorsConfigurationSource();

        // Non ne ho idea che significa /**, ma ora gli passo la configurazione che ho creato
        sorgente.registerCorsConfiguration("/**",corsConfiguration);
        return sorgente;

    }

    // La prima cosa che faccio, è agire sulle varie catene di sicurezza e specifico i vari comportamenti.
    // Spring Security si occuperà della gestione HTTP e la manderà a questo metodo.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        // 1. Disattivo il CSRF. Lo disattivo perchè in un architettura con Jwt STATELESS, non è necessario
            http.csrf(csrf ->csrf.disable());
        // 2. Definisco come le richieste HTTP vengono autorizzate.
            // Ora stesso discorso per le autorizzazioni. Sull'oggetto che gestisce le authHttp, determino la seguente:

            http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

            http.authorizeHttpRequests(auth -> auth
                    // Definisco su quali endpoint voglio usare l'autenticazione.
                    .requestMatchers("/autenticazione/**","/swagger-ui/**","/v3/api-docs/**", "/swagger-ui.html")
                    // Dato che l'autenticazione deve essere accessibile a tutti, definisco che il traffico su quell'endpoint è permesso...
                    .permitAll()
                    //... per qualsiasi richiesta
                    .anyRequest()
                    //... SOLO se autenticate.
                    .authenticated());
            http.sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            )// Ora che ho definito come viene gestita la Sessione, definisco quale è il filtro prima di rispondere.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Ora che ho la configurazione bella configurata, ritorno l'oggetto DefaultSecurityFilterChain configurato tramite HttpSecurity.
            return http.build();
    }

    /**
     * In questa maniera, possiamo usare questo gestore di autenticazione per poterlo iniettare in {@code AuthService}.
     * @param configuration
     * @return
     * @throws Exception
     */

    // Adesso per poter usare il JWT, usiamo un AuthenticationManager per la gesitione dell'autenticazione.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // Il metodo che crittografa la password tramite BCrypt.
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
// VECCHIO METODO SE DOVEVO SALVARMI IN MEMORIA GLI UTENTI ALLA PARTENZA
//    /*
//    Una volta configurate le varie catene (per ora 2), testo la catena tramite il form. Per poterlo fare,
//    devo crerare l'oggetto UserDetails per poter creare i singoli utenti e anche gli admin.
//     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        // Creo due utenti, uno normale e uno admin.
//        UserDetails utente = User.builder()
//                .username("utente")
//                // Ora cripto la password tramite il metodo precedente.
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        // Configurazione admin:
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        // Adesso salvo in memoria l'utente e l'admin creati.
//        return new InMemoryUserDetailsManager(utente,admin);
//    }

    /*
    Per usare l'autenticazione tarmite data base, usiamo un ulteriore layer di sicurezza fatto apposta per l'autenticazione
    definita dall'esterno. Quindi sostanzialmente è un AuthenticationProvider. Infatti, la classe Spring che si occupa dell'autenticazione
    con il mondo esterno (database), si chiama DaoAuthenticationProvider.
    Essa vuole:
        1. Il service per recuperarne l'oggetto Utente tramite il repository e trasformarlo in uno UserDetails.
        2. L'encoder della password, che abbiamo in questa classe.
     Spring Security all'avvio della appliaczione, imposterà come AuthenticationProvider QUESTO metodo. Quindi da ora in poi, le autenticazioni le deleghiamo
     al MioUserDetailsService, che comunica con il database tramtie UtenteRepository.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(MioUserDeatilsService mioUserDeatilsService, PasswordEncoder passwordEncoder){
        // Instanzio l'oggetto di ritorno, settandogli da subito l'utente (è Spring Security che lo gestisce.)
        DaoAuthenticationProvider daoAuthenticationProviderRet = new DaoAuthenticationProvider(mioUserDeatilsService);
        // Non esiste un costruttore con UserDetails e PasswordEncoder, quindi lo devo settare e poi ritorno l'oggetto
        daoAuthenticationProviderRet.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProviderRet;
    }

    /*
    Grazie a Spring, possiamo fare le injection dei service passandoli come parametri della funzione. Funziona come la Constructor Injection,
    solo che a differenza della injection nel costruttore, facciamo la new con le 2 dipendenze.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService,MioUserDeatilsService mioUserDeatilsService) {
        return new JwtAuthenticationFilter(jwtService,mioUserDeatilsService);
    }
}
