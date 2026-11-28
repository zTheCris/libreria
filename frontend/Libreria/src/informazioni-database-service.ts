import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InformazioniDatabaseService {
  http = inject(HttpClient);

  libriNumero = signal(0);
  autoriNumero = signal(0);
  copieNumero = signal(0);
  generiNumero = signal(0);
  caseEditriciNumero = signal(0);
  getLibriNum(): Observable<number> {
    return this.http.get<number>("http://localhost:8080/libri/num");
  }
  getAutoriNum(): Observable<number> {
    return this.http.get<number>("http://localhost:8080/autori/num");
  }
  getCopieNum(): Observable<number> {
    return this.http.get<number>("http://localhost:8080/copie/num");
  }
  getGeneriNum(): Observable<number> {
    return this.http.get<number>("http://localhost:8080/generi/num");
  }
  getCaseEditriciNum(): Observable<number> {
    return this.http.get<number>("http://localhost:8080/case/num");
  }
}
