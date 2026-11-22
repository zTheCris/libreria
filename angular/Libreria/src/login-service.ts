import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtToken } from './jwt-token';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  http = inject(HttpClient);
  // Il token Jwt lo metto a disposizione di chiunque inietti questa classe, quindi lo metto a questo livello.

  jwt = signal<JwtToken | null>(null);

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
      localStorage.removeItem('token');
      this.jwt.set(null);
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
}
