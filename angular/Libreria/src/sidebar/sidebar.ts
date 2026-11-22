import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
mostraCatalogo() {
throw new Error('Method not implemented.');
}
  /*
  Voglio implementare la possibilità di mostrare un sottomenu qnado viene selezionata una voce.
  Devo tener traccia di tutti questi elementi tramite variabile.
  */
  elementoSelected = signal< string| null>(null);
  selezionaElemento(component:string){
    // Lo cambio solo se è diverso
    if(this.elementoSelected()!=component){
      this.elementoSelected.set(component);
    }// Se invece è lo stesso, permetto di far scomparire il menu a tendina
     else{
      this.elementoSelected.set(null);
    }
  }
}
