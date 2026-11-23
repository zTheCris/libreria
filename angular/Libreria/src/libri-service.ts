import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Libro } from './libro';
import { HttpClient } from '@angular/common/http';
import { LibroRequest } from './libro-request';

@Injectable({
  providedIn: 'root',
})
export class LibriService {

  // I vari endpoint di spring
  http = inject(HttpClient);
  listaLibri = signal<Libro[] | null>(null);

  getAll(): Observable<Libro[]>{
    return this.http.get<Libro[]>("http://localhost:8080/libri")
  }
  getByIsbn(isbn:number): Observable<Libro> {
    // Devo verificare che isbn sia valido!!
    return this.http.get<Libro>("http://localhost:8080/libri/"+isbn)
  }
  addLibro(libro:LibroRequest ): Observable<Libro>{
    return this.http.post<Libro>("http://localhost:8080/libri",libro)
  }

}
