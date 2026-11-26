import { HttpErrorResponse } from '@angular/common/http';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Autore } from '../../autore';
import { AutoriService } from '../../autori-service';
import { StringResponse } from '../../string-response';

@Component({
  selector: 'app-mostra-autori',
  imports: [],
  templateUrl: './mostra-autori.html',
  styleUrl: './mostra-autori.css',
})
export class MostraAutori implements OnInit {
  autoriService = inject(AutoriService);
  destroyRef = inject(DestroyRef);
  router = inject(Router);
  listaFiltrata = signal<Autore[] | null>([]);


  cerca(query: string) {
   if(!query){
      this.listaFiltrata.set(this.autoriService.listaAutori());
    }
    // La ricerca chiamerà il database per vedere quali autori hanno quel nome
    this.autoriService.getByNome(query).subscribe({
      next: (res) => {
        this.listaFiltrata.set(res);
      }
    })
  }
  modifica(id: number) {
    // Lo rimando sul component ModificaLibro
    this.router.navigate(['/autori/modifica/' + id]);
  }
 elimina(id: number) {
    Swal.fire({
      title: 'Sei sicuro?',
      text: 'Non potrai recuperare questo autore dopo averlo eliminato!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sì, elimina',
      cancelButtonText: 'Annulla',
    }).then((result) => {
      if (result.isConfirmed) {
        this.autoriService.eliminaAutore(id).subscribe({
          // ATTENZIONE: LA NEXT DELL'OBSERVABLE ENTRA SOLO SE C'E' UN JSON DI RISPOSTA!!!!!!!!!
          next: (res: StringResponse) => {
            Swal.fire({
              title: 'Eliminato!',
              text: res.result,
              icon: 'success',
            });
            this.autoriService.getAutori().subscribe((res) => {
              this.autoriService.listaAutori.set(res);
            });
          },
          error: (err: HttpErrorResponse) => {
            Swal.fire({
              title: 'Errore!',
              text: err.error?.messaggio ?? "Impossibile eliminare l'autore.",
              icon: 'error',
            });
          },
        });
        // Ora devo aggiornare la lista dei libri.
      }
    });
  }

  ngOnInit() {
    this.autoriService
      .getAutori()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (res: Autore[]) => {
          this.autoriService.listaAutori.set(res);
          this.listaFiltrata.set(res);
        },
        error: (err: HttpErrorResponse) => {
          Swal.fire({ title: '' });
        },
      });
  }
}
