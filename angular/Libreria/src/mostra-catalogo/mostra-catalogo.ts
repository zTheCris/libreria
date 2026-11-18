import { Component, DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { LibriService } from '../libri-service';

@Component({
  selector: 'app-mostra-catalogo',
  imports: [],
  templateUrl: './mostra-catalogo.html',
  styleUrl: './mostra-catalogo.css',
})
export class MostraCatalogo {
    libriService = inject(LibriService);
  destroyRef = inject(DestroyRef);
  constructor(){
    // Per default, mi prendo tutti i libri.
    this.libriService.getAll()
    .pipe(takeUntilDestroyed(this.destroyRef))
    .subscribe((res)=>{
      this.libriService.listaLibri.set(res);
    })
  }
}
