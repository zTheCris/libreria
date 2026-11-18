import { inject, Injectable } from '@angular/core';
import { Copia } from './copia';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CopieService {
  http = inject(HttpClient);


  getAll(): Observable<Copia[]> {
    return this.http.get<Copia[]>("http://localhost:8080/copie");
  }
}
