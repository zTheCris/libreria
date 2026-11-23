import { Component, inject } from '@angular/core';
import { LibriService } from '../../libri-service';
import { Libro } from '../../libro';
import { FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import { AutoriService } from '../../autori-service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Autore } from '../../autore';
import { LibroRequest } from '../../libro-request';
@Component({
  selector: 'app-aggiungi-libro',
  imports: [ReactiveFormsModule],
  templateUrl: './aggiungi-libro.html',
  styleUrl: './aggiungi-libro.css',
})
export class AggiungiLibro {


  libroForm = new FormGroup({
    isbn: new FormControl<number | null>(0),
    titolo: new FormControl(''),
    anno: new FormControl(''),
    genere: new FormControl(''),
    prezzo: new FormControl(0),
    // Nel form, permetto solo di salvare un autore alla volta.
    autore: new FormControl<Autore | null>(null)
  })
  // Dato che ci possono essere piu' autori, tengo traccia degli autori selezionati.
  autoriSelezionati: Autore[] = [];
  autoriIDs: number[] = [];

  // Dato che voglio aggiungere piu' option, mi tengo conto di quante volte il bottone è stato cliccato.
  numeroAutori = [0];
  // Quando il bottone + viene cliccato, aggiungo un elemento.


  libriService = inject(LibriService);
  autoriService = inject(AutoriService);
  /**
   * Aggiunge all'array di Autori un elemento intero. Serve come "contenitore" per il for dinamico
   * di Angular per poter creare più select. (menu a tendina)
   */
  aumenta() {
    this.numeroAutori.push(this.numeroAutori.length)
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


  aggiungiLibro(){
    // Trasformo il form in oggetto Libro

    const dataForm = this.libroForm.getRawValue();
    // Per ogni autore, prendi l'id e mettilo nell'array di autoriIDs da mandare a Spring.
    this.autoriSelezionati.forEach(element => {
      this.autoriIDs.push(element.idAutore)
    });
    const libro: LibroRequest = {
      // Devo convertirlo a number e basta, non può essere number | null | undefined
      isbn: this.libroForm.value?.isbn ??  0,
      titolo: this.libroForm.value?.titolo ?? '',
      data: this.libroForm.value?.anno ?? '',
      genere: this.libroForm.value?.genere ?? '',
      prezzo: this.libroForm.value?.prezzo ?? 0,
      autoriIDs: this.autoriIDs ?? []
    }
    console.log(libro);
    this.libriService.addLibro(libro).subscribe({
      next: (res: Libro) => {
        Swal.fire({title:"Libro aggiunto correttamente.",icon:'success'})
      },
      error: (err:HttpErrorResponse) => {
        // Dal body di risposta, estraggo il campo "messaggio".
        Swal.fire({title:err.error?.messaggio,icon:'error'});
      }
    })
  }
  constructor(){
    this.autoriService.getAutori().subscribe((res)=> {
      this.autoriService.listaAutori.set(res);
      // this.autoriService.listaAutori()?.forEach(autore => console.log(autore));
    });
  }

}
