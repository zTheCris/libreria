import { StringResponse } from '../../string-response';
import { Component, DestroyRef, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { LibriService } from '../../libri-service';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { Libro } from '../../libro';

@Component({
  selector: 'app-mostra-catalogo',
  imports: [],
  templateUrl: './mostra-catalogo.html',
  styleUrl: './mostra-catalogo.css',
})
export class MostraCatalogo {
  listaFiltrata = signal<Libro[] | null>(null);
  cerca(query: string) {
    if(!query){
      this.listaFiltrata.set(this.libriService.listaLibri());
    }
    // La ricerca chiamerà il database per vedere quali titoli hanno quel titolo
    this.libriService.getByTitolo(query).subscribe({
      next: (res) => {
        this.listaFiltrata.set(res);
      }
    })
  }
  modifica(id: number) {
    this.router.navigate(['/libri/modificalibro/' + id]);
  }
  libriService = inject(LibriService);
  destroyRef = inject(DestroyRef);
  router = inject(Router);

  elimina(id: number) {
    Swal.fire({
      title: 'Sei sicuro?',
      text: 'Non potrai recuperare questo libro dopo averlo eliminato!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sì, elimina',
      cancelButtonText: 'Annulla',
    }).then((result) => {
      if (result.isConfirmed) {
        this.libriService.eliminaLibro(id).subscribe({
          // ATTENZIONE: LA NEXT DELL'OBSERVABLE ENTRA SOLO SE C'E' UN JSON DI RISPOSTA!!!!!!!!!
          next: (res: StringResponse) => {
            Swal.fire({
              title: 'Eliminato!',
              text: res.result,
              icon: 'success',
            });
            this.libriService.getAll().subscribe((res) => {
              this.libriService.listaLibri.set(res);
            });
          },
          error: (err: HttpErrorResponse) => {
            Swal.fire({
              title: 'Errore!',
              text: err.error?.messaggio ?? 'Impossibile eliminare il libro.',
              icon: 'error',
            });
          },
        });
        // Ora devo aggiornare la lista dei libri.
      }
    });
  }

  constructor() {
    // Per default, mi prendo tutti i libri.
    this.libriService
      .getAll()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((res) => {
        this.libriService.listaLibri.set(res);
        // Lo aggiungo anche alla lista filtrata, cosi da poter implementare la ricerca.
        this.listaFiltrata.set(res);
      });
  }
}
