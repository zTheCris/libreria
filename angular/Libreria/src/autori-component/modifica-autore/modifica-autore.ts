import { ActivatedRoute, Routes } from '@angular/router';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { AutoriService } from '../../autori-service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { AutoreRequest } from '../../autore-request';
import { Autore } from '../../autore';

@Component({
  selector: 'app-modifica-autore',
  imports: [ReactiveFormsModule],
  templateUrl: './modifica-autore.html',
  styleUrl: './modifica-autore.css',
})
export class ModificaAutore implements OnInit {
  formAutori = new FormGroup({
    nome: new FormControl(''),
    cognome: new FormControl(''),
    data: new FormControl(''),
    nazionalita: new FormControl(''),
  });
  autoriService = inject(AutoriService);
  idAutore = -1;
  route = inject(ActivatedRoute);
  autore = signal<Autore | null>(null);

  modificaAutore() {
    // Mi creo l'oggetto AutoreRequest dai campi del form
    const autore: AutoreRequest = {
      nome: this.formAutori.value?.nome ?? '',
      cognome: this.formAutori.value?.cognome ?? '',
      data: this.formAutori.value?.data ?? '',
      nazionalita: this.formAutori.value?.nazionalita ?? '',
    };
    // Lo mando al service
    this.autoriService.modificaAutore(autore).subscribe({
      next: () => {
        Swal.fire({ title: 'Autore aggiunto correttamente!', icon: 'success' });
      },
      error: (err: HttpErrorResponse) => {
        // Nel JSON di risposta, fatto con Spring,
        // se l'eccezione è stata colta dal GlobalExceptionHandler,
        // ci sarà la chiave "messaggio", con il contenuto dell'eccezione
        Swal.fire({ title: err.error?.messaggio, icon: 'error' });
      },
    });
  }

  popolaForm() {
    this.formAutori.patchValue({
      nome: this.autore()?.nome,
      cognome: this.autore()?.cognome,
      data: this.autore()?.data,
      nazionalita: this.autore()?.nazionalita.nome,
    });
  }

  caricaAutore() {
    this.autoriService.getById(this.idAutore).subscribe({
      next: (res: Autore) => {
        this.autore.set(res);
        this.popolaForm();
      },
      error: () => {
        Swal.fire({
          title: 'Autore non trovato',
          icon: 'error',
        });
      },
    });
  }

  chiediIdAutore() {
    Swal.fire({
      title: "Inserisci l'ID dell'autore da modificare",
      input: 'number',
      inputPlaceholder: 'ID autore',
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
        this.idAutore = Number(result.value);
        this.caricaAutore();
      }
    });
  }

  ngOnInit() {
    // Mi recupero l'id dalla url
    // Recupero l'ID dell'autore  dalla URL.
    if (this.route.snapshot.params['id']) {
      this.idAutore = Number(this.route.snapshot.params['id']);
      this.caricaAutore();
    } else {
      this.chiediIdAutore();
    }
  }
}
