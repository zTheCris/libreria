import { Nazionalita } from './../../nazionalita';
import { Component, inject, signal } from '@angular/core';
import { LibriService } from '../../libri-service';
import { Libro } from '../../libro';
import { FormGroup, FormControl, ReactiveFormsModule, ɵInternalFormsSharedModule } from '@angular/forms';
import { AutoriService } from '../../autori-service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Autore } from '../../autore';
import { LibroRequest } from '../../libro-request';
import { RouterLink } from '@angular/router';
import { AutoreRequest } from '../../autore-request';
@Component({
  selector: 'app-add-autore',
  imports: [ReactiveFormsModule],
  templateUrl: './add-autore.html',
  styleUrl: './add-autore.css',
})
export class AddAutore {

  formAutori = new FormGroup({
    nome: new FormControl(''),
    cognome: new FormControl(''),
    data: new FormControl(''),
    nazionalita: new FormControl('')
  })
  autoriService = inject(AutoriService);


  aggiungiAutore() {
    // Mi creo l'oggetto AutoreRequest dai campi del form
    const autore: AutoreRequest = {
      nome: this.formAutori.value?.nome ?? '',
      cognome: this.formAutori.value?.cognome ?? '',
      data: this.formAutori.value?.data ?? '',
      nazionalita: this.formAutori.value?.nazionalita ?? ''
    }
    // Lo mando al service
    this.autoriService.addAutore(autore).subscribe({
      next :() => {
        Swal.fire({title:"Autore aggiunto correttamente!",icon:'success'})
      },
      error: (err:HttpErrorResponse) => {
        // Nel JSON di risposta, fatto con Spring,
        // se l'eccezione è stata colta dal GlobalExceptionHandler,
        // ci sarà la chiave "messaggio", con il contenuto dell'eccezione
         Swal.fire({title:err.error?.messaggio,icon:'error'});
      },
    })
  }

}
