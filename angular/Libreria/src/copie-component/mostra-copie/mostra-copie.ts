import { CopieService } from './../../copie-service';
import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Copia } from '../../copia';

@Component({
  selector: 'app-mostra-copie',
  imports: [RouterLink],
  templateUrl: './mostra-copie.html',
  styleUrl: './mostra-copie.css',
})
export class MostraCopie implements OnInit{
elimina(arg0: number) {
throw new Error('Method not implemented.');
}
modifica(arg0: number) {
throw new Error('Method not implemented.');
}
test() {
throw new Error('Method not implemented.');
}
  copieService = inject(CopieService);
  listaFiltrata = signal<Copia[] | null>([]);
  cerca(query: string) {
    throw new Error('Method not implemented.');
  }

  ngOnInit(){
    // Chiamo il service delle copie
    this.copieService.getAll()
    .pipe()
    .subscribe({
      next: (res: Copia[]) =>{
        this.copieService.listaCopie.set(res);
        this.listaFiltrata.set(res);
      }
    })
  }
}
