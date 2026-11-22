import { Component, inject } from '@angular/core';
import { LibriService } from '../libri-service';
import { Libro } from '../libro';
import { FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import { AutoriService } from '../autori-service';
@Component({
  selector: 'app-aggiungi-libro',
  imports: [ReactiveFormsModule],
  templateUrl: './aggiungi-libro.html',
  styleUrl: './aggiungi-libro.css',
})
export class AggiungiLibro {

  libroForm = new FormGroup({

  })
  libriService = inject(LibriService);
  autoriService = inject(AutoriService);

  aggiungiLibro(){

  }
  constructor(){
    this.autoriService.getAutori().subscribe((res)=> {
      this.autoriService.listaAutori.set(res);
    })
  }
}
