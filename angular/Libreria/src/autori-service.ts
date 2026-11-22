import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Autore } from './autore';

@Injectable({
  providedIn: 'root',
})
export class AutoriService {
  http = inject(HttpClient);
  listaAutori = signal<Autore[] | null>(null);
  getAutori(): Observable<Autore[]> {
    return this.http.get<Autore[]>("http://localhost:8080/autori");
  }
}
