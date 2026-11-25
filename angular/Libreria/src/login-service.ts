import { StringResponse } from './string-response';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtToken } from './jwt-token';
import Swal from 'sweetalert2';
import {jwtDecode} from 'jwt-decode';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root',
})
export class LoginService {
  http = inject(HttpClient);
  // Il token Jwt lo metto a disposizione di chiunque inietti questa classe, quindi lo metto a questo livello.

  jwt = signal<JwtToken | null>(null);
  router = inject(Router);
  // Uso il jwtDecode della libreria jwt-decode per sapere se il token è scaduto


  // Si occupa di interrogare gli endpoint spring /autenticazione
  login(username: string, password: string): Observable<JwtToken> {
    return this.http.post<JwtToken>('http://localhost:8080/autenticazione/login', {
      username: username,
      password: password,
    });
  }
  // Adesso: se ho trovato il Jwt, lo devo mettere nel localStorage(oltre che a metterlo a disposizione in questa classe).
  salvaToken(jwt: JwtToken) {
    this.jwt.set(jwt);
    // Metto in localStorage il token (chiave), e la stringa del token (valore)
    localStorage.setItem('token', jwt.token);
  }
  logout() {
    if(this.getToken()===null){
      Swal.fire({title:"Non sei loggato.",icon: 'error'});
    }
    else{
      this.rimuoviToken()
      Swal.fire({title:'Logout effettuato.',icon:'success'})
    }
  }

  // Vedo se nel localStorage c'è la stringa localStorage
  getToken(): string | null {
    // Per capire se localStorage non è stato impostato, ed evitare il blocco sulla root,
    // vedo se localStorage è undefined.

    if (typeof localStorage === 'undefined') {
      return null;

    }

    return localStorage.getItem('token');
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    // Se il token è null, ritorno true
    if(!token)
      return true
    try{
      // Nel JWT il campo sulla scadenza è espresso in secondi, ma in JS ci serve in millisecondi.

      // Creo un oggetto JSON che ha solo la scadenza e la
      const decodificato = jwtDecode(token);
      // Estraggo il campo exp
      const exp = decodificato.exp;
      const scaduto = exp! * 1000 < Date.now();
      // Controllo se il giorno della scadenza, convertito in millisecondi,
      // E' minore di oggi, in millisecondi
      // Operatore ternario per levare l'if.
      return scaduto ? (this.rimuoviToken(),true): false;


    } catch{
      // Se il token è scaduto, lo rimuovo dal localStorage
      return true;
    }
  }

  rimuoviToken() {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem('token');
    }

    this.jwt.set(null);
  }
  ping(): Observable<StringResponse>{
    return this.http.get<StringResponse>("http://localhost:8080/autenticati/ping");
  }

}
