import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { Autore } from '../../autore';
import { AutoriService } from '../../autori-service';
import { LibriService } from '../../libri-service';
import { Libro } from '../../libro';
import { LibroRequest } from '../../libro-request';
import { ActivatedRoute } from '@angular/router';
import { Genere } from '../../genere';

@Component({
  selector: 'app-modifica-libro',
  imports: [ReactiveFormsModule],
  templateUrl: './modifica-libro.html',
  styleUrl: './modifica-libro.css',
})
export class ModificaLibro {
  libroForm = new FormGroup({
    isbn: new FormControl<number | null>(0),
    titolo: new FormControl(''),
    anno: new FormControl(''),
    genere: new FormControl(''),
    prezzo: new FormControl(0),
    // Nel form, permetto solo di salvare un autore alla volta.
    autore: new FormControl<Autore | null>(null),
  });

  // Dato che ci possono essere piu' autori, tengo traccia degli autori selezionati.
  autoriSelezionati: Autore[] = [];
  autoriIDs: number[] = [];

  // Dato che voglio aggiungere piu' option, mi tengo conto di quante volte il bottone è stato cliccato.
  numeroAutori = [0];
  // Quando il bottone + viene cliccato, aggiungo un elemento.

  idLibro = -1;
  route: ActivatedRoute = inject(ActivatedRoute);
  libriService = inject(LibriService);
  autoriService = inject(AutoriService);
  // I parametri del libro che metterò nel form
  libro = signal<Libro | null>(null);

  /**
   * Aggiunge all'array di Autori un elemento intero. Serve come "contenitore" per il for dinamico
   * di Angular per poter creare più select. (menu a tendina)
   */
  aumenta() {
    this.numeroAutori.push(this.numeroAutori.length);
  }
  /**
   * Aggiunge un autore all'array di Autori.
   */
  aggiungiAutore() {
    const autore = this.libroForm.value?.autore;

    if (autore) {
      this.autoriSelezionati.push(autore);
    }
  }
  aggiungiAutoreParam(autore: Autore) {
    if (autore) {
      this.autoriSelezionati.push(autore);
    }
  }

  aggiungiLibro() {
    // Trasformo il form in oggetto Libro

    const dataForm = this.libroForm.getRawValue();
    // Per ogni autore, prendi l'id e mettilo nell'array di autoriIDs da mandare a Spring.
    this.autoriSelezionati.forEach((element) => {
      this.autoriIDs.push(element.idAutore);
    });
    const libro: LibroRequest = {
      // Devo convertirlo a number e basta, non può essere number | null | undefined
      isbn: this.libroForm.value?.isbn ?? 0,
      titolo: this.libroForm.value?.titolo ?? '',
      data: this.libroForm.value?.anno ?? '',
      genere: this.libroForm.value?.genere ?? '',
      prezzo: this.libroForm.value?.prezzo ?? 0,
      autoriIDs: this.autoriIDs ?? [],
    };

    console.log(libro);
    this.libriService.addLibro(libro).subscribe({
      next: (res: Libro) => {
        Swal.fire({ title: 'Libro aggiunto correttamente.', icon: 'success' });
      },
      error: (err: HttpErrorResponse) => {
        // Dal body di risposta, estraggo il campo "messaggio".
        Swal.fire({ title: err.error?.messaggio, icon: 'error' });
      },
    });
  }
  popolaForm() {
    this.libroForm.patchValue({
      isbn: this.libro()?.isbn,
      titolo: this.libro()?.titolo,
      anno: this.libro()?.anno,
      genere: this.libro()?.genere?.nome,
      prezzo: this.libro()?.prezzo,
    });
    this.autoriSelezionati = this.libro()?.autori ?? [];
    // Adesso per poterli visualizzare, uso la variabile numeroAutori per aggiungere
    // tanti elementi quanti autori.
    for (let index = 0; index < this.autoriSelezionati.length; index++) {
      this.aumenta();
      // Per ogni select, compilare i campi autore.
      // this.libroForm.addControl('autore'+index, new FormControl<Autore | null>(this.autoriSelezionati[index]))
    }

  }

  constructor() {
    // Vedo se sulla URL c'è l'ID della casa da modificare.
    if (this.route.snapshot.params['id']) {
      // Ho trovato l'id del libro da modificare. Chiamo getById
      this.idLibro = this.route.snapshot.params['id'];
      this.libriService.getByIsbn(this.idLibro).subscribe({
        next: (res: Libro) => {
          console.log(res);
          this.libro.set(res);
          // Ora aggiorno il form per autocompletarlo
          this.popolaForm();
          console.log(this.autoriSelezionati);
        },
        error: () => {
          Swal.fire({
            title: 'Libro non trovato',
            icon: 'error',
          });
        },
      });

      this.autoriService.getAutori().subscribe((res) => {
        this.autoriService.listaAutori.set(res);
        // this.autoriService.listaAutori()?.forEach(autore => console.log(autore));
      });
    } else {
      // Chiedo all'utente l'id del libro
      Swal.fire({
        title: 'Inserisci l’ID del libro da modificare',
        input: 'number',
        inputPlaceholder: 'ID libro',
        inputAttributes: {
          min: '1',
          step: '1',
        },
        showCancelButton: true,
        confirmButtonText: 'Cerca',
        cancelButtonText: 'Annulla',
        inputValidator: (value) => {
          if (!value || Number(value) <= 0 || !Number.isInteger(Number(value))) {
            return 'Inserisci un ID valido!';
          }
          return null;
        },
      }).then((result) => {
        if (result.isConfirmed) {
          this.idLibro = Number(result.value);

          // Qui chiamerai il servizio per recuperare il libro
          this.libriService.getByIsbn(this.idLibro).subscribe({
            next: (res) => {
              this.libro.set(res);
              this.popolaForm();
            },
            error: () => {
              Swal.fire({
                title: 'Libro non trovato',
                icon: 'error',
              });
            },
          });
        }
      });
    }
  }
}
