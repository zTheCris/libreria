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
  http = inject(HttpClient);
  listaAutori = signal<Autore[] | null>(null);


  addAutore(autore: AutoreRequest): Observable<Autore> {
    return this.http.post<Autore>("http://localhost:8080/autori",autore)
  }
  eliminaAutore(id: number): Observable<StringResponse> {
    return this.http.delete<StringResponse>("http://localhost:8080/autori/"+id)
  }
  modificaAutore(autore: AutoreRequest): Observable<Autore>{
    return this.http.put<Autore>("http://localhost:8080/autori",autore)
  }

  getAutori(): Observable<Autore[]> {
    return this.http.get<Autore[]>("http://localhost:8080/autori");
  }

  getByNome(nome:string): Observable<Autore[]>{
    return this.http.get<Autore[]>("http://localhost:8080/autori/nome/"+nome);
  }
  getById(id:number): Observable<Autore>{
    return this.http.get<Autore>("http://localhost:8080/autori/"+id)
  }
}
