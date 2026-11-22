import { Component, DestroyRef, inject, signal } from '@angular/core';
import { InformazioniDatabaseService } from '../informazioni-database-service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-informazioni-database',
  templateUrl: './informazioni-database.html',
  styleUrl: './informazioni-database.css',
})
export class InformazioniDatabase {
  informazioniDatabaseService = inject(InformazioniDatabaseService);

  destroyRef = inject(DestroyRef);

  constructor() {
    // Recupero con il service il numero di libri, autori e copie dal database, sottoscrivendomi all'Observable (che sarebbe la risposta Promise)
    this.informazioniDatabaseService
      .getLibriNum()
      // Se la pagina cambia mentre aspetto, lo distruggo
      .pipe(takeUntilDestroyed(this.destroyRef))
      // Altrimenti, mi iscrivo all'observable.
      .subscribe(
        {
          next: (risposta: number) => {
            this.informazioniDatabaseService.libriNumero.set(risposta);
          },
          error: (err: HttpErrorResponse) => {
            // Tramite Sweetalert mando un errore visivo sulla pagina.
            Swal.fire({ title: err.message, icon: 'error' });
          }
        }
    );

    this.informazioniDatabaseService
      .getAutoriNum()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
        {
          next: (risposta: number) => {
            this.informazioniDatabaseService.autoriNumero.set(risposta);
          },
          error: (err: HttpErrorResponse) => {
            Swal.fire({ title: err.message, icon: 'error' });
          }
        }
      );

    // Setto il numero delle copie
    this.informazioniDatabaseService
      .getCopieNum()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
        {
          next: (risposta: number) => {
            this.informazioniDatabaseService.copieNumero.set(risposta);
          },
          error: (err: HttpErrorResponse) => {
            Swal.fire({ title: err.message, icon: 'error' });
          }
        }
      );

    // Setto il numero dei generi
    this.informazioniDatabaseService
      .getGeneriNum()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
        {
          next: (risposta: number) => {
            this.informazioniDatabaseService.generiNumero.set(risposta);
          },
          error: (err: HttpErrorResponse) => {
            Swal.fire({ title: err.message, icon: 'error' });
          }
        }
    );

    // Setto il numero delle case editrici
    this.informazioniDatabaseService
      .getCaseEditriciNum()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
        {
          next: (risposta: number) => {
            this.informazioniDatabaseService.caseEditriciNumero.set(risposta);
          },
          error: (err: HttpErrorResponse) => {
            Swal.fire({ title: err.message, icon: 'error' });
          }
        }
      );
  }
}
