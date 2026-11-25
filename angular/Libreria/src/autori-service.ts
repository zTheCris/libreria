import { StringResponse } from './string-response';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Autore } from './autore';
import { AutoreRequest } from './autore-request';

@Injectable({
  providedIn: 'root',
})
export class AutoriService {
  addAutore(autore: AutoreRequest): Observable<Autore> {
    return this.http.post<Autore>("http://localhost:8080/autori",autore)
  }
  eliminaAutore(id: number): Observable<StringResponse> {
    return this.http.delete<StringResponse>("http://localhost:8080/autori/"+id)
  }
  http = inject(HttpClient);
  listaAutori = signal<Autore[] | null>(null);
  getAutori(): Observable<Autore[]> {
    return this.http.get<Autore[]>("http://localhost:8080/autori");
  }
}
