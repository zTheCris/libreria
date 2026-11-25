
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
    autore: new FormControl<Autore | null>(null),
  });

  // Tengo traccia degli autori associati al libro.
  autoriSelezionati: Autore[] = [];

  // Lista degli autori disponibili nella select.
  autoriFiltrata = signal<Autore[]>([]);

  idLibro = -1;

  route = inject(ActivatedRoute);
  libriService = inject(LibriService);
  autoriService = inject(AutoriService);

  // Contiene il libro da modificare.
  libro = signal<Libro | null>(null);

  aggiungiAutore() {
    const autore = this.libroForm.controls.autore.value;

    if (autore && this.autoriSelezionati.some(
      a => a.idAutore === autore.idAutore
    )) {
      Swal.fire({ title: 'Autore già selezionato.', icon: 'error' });
      return;
    }

    if (autore) {
      this.autoriSelezionati.push(autore);

      this.autoriFiltrata.set(
        this.autoriService.listaAutori()?.filter(
          a => !this.autoriSelezionati.some(
            selezionato => selezionato.idAutore === a.idAutore
          )
        ) ?? []
      );
    }

    // Riporto la select al placeholder.
    this.libroForm.controls.autore.setValue(null);
  }

  rimuoviAutore(autore: Autore) {
    this.autoriSelezionati = this.autoriSelezionati.filter(
      a => a.idAutore !== autore.idAutore
    );

    // Rendo nuovamente disponibile l'autore nella select.
    this.autoriFiltrata.set(
      this.autoriService.listaAutori()?.filter(
        a => !this.autoriSelezionati.some(
          selezionato => selezionato.idAutore === a.idAutore
        )
      ) ?? []
    );
  }

  aggiungiLibro() {
    const autoriIDs = this.autoriSelezionati.map(
      autore => autore.idAutore
    );

    const libro: LibroRequest = {
      isbn: this.libroForm.value.isbn ?? 0,
      titolo: this.libroForm.value.titolo ?? '',
      data: this.libroForm.value.anno ?? '',
      genere: this.libroForm.value.genere ?? '',
      prezzo: this.libroForm.value.prezzo ?? 0,
      autoriIDs: autoriIDs
    };

    console.log(libro);

    // ATTENZIONE: sostituisci updateLibro con il nome
    // effettivo del tuo metodo di aggiornamento.
    this.libriService.updateLibro(this.idLibro, libro).subscribe({
      next: (res:Libro) => {
        Swal.fire({
          title: 'Libro modificato correttamente.',
          icon: 'success'
        });
        this.libro.set(res);
      },
      error: (err: HttpErrorResponse) => {
        Swal.fire({
          title: err.error?.messaggio,
          icon: 'error'
        });
      }
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

    // Recupero gli autori già associati al libro.
    this.autoriSelezionati = [...(this.libro()?.autori ?? [])];

    // Escludo dalla select gli autori già presenti nel libro.
    this.autoriFiltrata.set(
      this.autoriService.listaAutori()?.filter(
        autore => !this.autoriSelezionati.some(
          selezionato => selezionato.idAutore === autore.idAutore
        )
      ) ?? []
    );
  }

  constructor() {

    // Carico prima la lista degli autori.
    this.autoriService.getAutori().subscribe({
      next: (res) => {
        this.autoriService.listaAutori.set(res);
        this.autoriFiltrata.set(res);

        // Recupero l'ID del libro dalla URL.
        if (this.route.snapshot.params['id']) {
          this.idLibro = Number(this.route.snapshot.params['id']);
          this.caricaLibro();
        } else {
          this.chiediIdLibro();
        }
      }
    });
  }

  caricaLibro() {
    this.libriService.getByIsbn(this.idLibro).subscribe({
      next: (res: Libro) => {
        this.libro.set(res);
        this.popolaForm();
      },
      error: () => {
        Swal.fire({
          title: 'Libro non trovato',
          icon: 'error'
        });
      }
    });
  }

  chiediIdLibro() {
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
        this.caricaLibro();
      }
    });
  }
}
