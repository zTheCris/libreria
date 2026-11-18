import { Component, DestroyRef, inject, signal } from '@angular/core';
import { InformazioniDatabaseService } from '../informazioni-database-service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
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
    this.informazioniDatabaseService.getLibriNum()
    // Se la pagina cambia mentre aspetto, lo distruggo
    .pipe(takeUntilDestroyed(this.destroyRef))
    // Altrimenti, mi iscrivo all'observable.
    .subscribe((num) => {
      // console.log("Numero di libri: " + num);
      this.informazioniDatabaseService.libriNumero.set(num);
    });

    this.informazioniDatabaseService.getAutoriNum()
    .pipe(takeUntilDestroyed(this.destroyRef))
    .subscribe((num) => {
      // console.log("Numero di autori: " + num);
      this.informazioniDatabaseService.autoriNumero.set(num);
    });

    this.informazioniDatabaseService.getCopieNum()
    .pipe(takeUntilDestroyed(this.destroyRef))
    .subscribe((num) => {
      // console.log("Numero di copie: " + num);
      this.informazioniDatabaseService.copieNumero.set(num);
    });

    this.informazioniDatabaseService.getGeneriNum()
    .pipe(takeUntilDestroyed(this.destroyRef))
    .subscribe((num) => {
      // console.log("Numero di generi: " + num);
      this.informazioniDatabaseService.generiNumero.set(num);
    });

    this.informazioniDatabaseService.getCaseEditriciNum()
    .pipe(takeUntilDestroyed(this.destroyRef))
    .subscribe((num) => {
      // console.log("Numero di case editrici: " + num);
      this.informazioniDatabaseService.caseEditriciNumero.set(num);
    });

  }
}
