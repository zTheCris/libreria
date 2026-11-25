import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Libro } from './libro';
import { HttpClient } from '@angular/common/http';
import { LibroRequest } from './libro-request';
import { StringResponse } from './string-response';

@Injectable({
  providedIn: 'root',
})
export class LibriService {



  // I vari endpoint di spring
  http = inject(HttpClient);
  listaLibri = signal<Libro[] | null>(null);

    /*
  =================================================
                        GET
  =================================================
  */
  getAll(): Observable<Libro[]>{
    return this.http.get<Libro[]>("http://localhost:8080/libri")
  }
  getByIsbn(isbn:number): Observable<Libro> {
    // Devo verificare che isbn sia valido!!
    return this.http.get<Libro>("http://localhost:8080/libri/"+isbn)
  }
  getByTitolo(query: string):Observable<Libro[]> {
    return this.http.get<Libro[]>("http://localhost:8080/libri/getallbytitle/"+query)
  }

  /*
  =================================================
                        POST
  =================================================
  */
  addLibro(libro:LibroRequest ): Observable<Libro>{
    return this.http.post<Libro>("http://localhost:8080/libri",libro)
  }
    /*
  =================================================
                        PUT
  =================================================
  */
  updateLibro(id:number,libro:LibroRequest): Observable<Libro>{
    return this.http.put<Libro>("http://localhost:8080/libri",libro)
  }

  /*
  =================================================
                        DELETE
  =================================================
  */

  // Ho avuto un problema con la next per l'Observable.
  // Il next si aspetta un JSON se la risposta è 200, se non c'è un JSON non entra mai nella next.
  // In Spring ho creato la classe StringDTO cosi da poter mandare una Stringa in JSON.
  eliminaLibro(id: number): Observable<StringResponse>{
    return this.http.delete<StringResponse>("http://localhost:8080/libri/"+id,
    );
  }

}
