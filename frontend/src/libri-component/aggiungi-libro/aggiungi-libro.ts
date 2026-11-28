import { Component, inject, signal } from '@angular/core';
import { LibriService } from '../../libri-service';
import { Libro } from '../../libro';
import { FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import { AutoriService } from '../../autori-service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Autore } from '../../autore';
import { LibroRequest } from '../../libro-request';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-aggiungi-libro',
  imports: [ReactiveFormsModule, RouterLink],
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

  libriService = inject(LibriService);
  autoriService = inject(AutoriService);
  // Mi serve per filtrare la select quando ho inserito gli autori.
  autoriFiltrata = signal<Autore[]>([]);
  /**
   * Aggiunge un autore all'array di Autori.
   */
  aggiungiAutore() {

    const autore = this.libroForm.value?.autore;

    if(this.autoriSelezionati.includes(autore!)){
      Swal.fire({title:"Autore già selezionato.",icon:'error'})
      return;
    }

    if (autore) {
      this.autoriSelezionati.push(autore);
      // Ora filtro la autoriFiltrata
      this.autoriFiltrata.set(this.autoriService.listaAutori()?.filter(
          // La filter torna una lista filtrata.
          // Dico al predicato: "Filtra la lista per cui l'autore NON è incluso nella lista degli autoriSelezionati"
          autore =>  !this.autoriSelezionati.includes(autore)
        ) ?? []
      );
    }
    // Adesso se ho fatto tutto, setto il valore dell'option a null. Dato che uso [ngValue], Angular sa che quando "autore" del formControl è null, si setta quell'opzione.
    this.libroForm.controls.autore.setValue(null);
  }
  rimuoviAutore(autore: Autore) {
     if (autore) {
      // La differenza ora è che devo ricreare l'array degli autoriSelezionati ESCLUDENDO quello rimosso.
      this.autoriSelezionati = this.autoriSelezionati.filter(
        // Verifica che l'id dell'autoreArrowFun E' diverso dall'autore che l'utente ha rimosso. Se si, filtra.
        autoreArrowFun => autoreArrowFun.idAutore !== autore.idAutore
      )

      // Ora filtro la autoriFiltrata
      this.autoriFiltrata.set(this.autoriService.listaAutori()!.filter(
        // La filter torna una lista filtrata.
        // Dico al predicato: "Filtra la lista per cui l'autore NON e' incluso nella lista degli autoriSelezionati"
        autore => !this.autoriSelezionati.includes(autore)))
    }
  }


  aggiungiLibro(){
    // Trasformo il form in oggetto Libro

    // Mi istanzio l'oggetto contenente i dati del form.

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
        // Resetto gli autoriIDs.
        this.autoriIDs = [];
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
      // Mi setto anche la filtrata per la select.
      this.autoriFiltrata.set(res);
      // this.autoriService.listaAutori()?.forEach(autore => console.log(autore));
    });
  }

}
