import { Component } from '@angular/core';
import { AzioniRapide } from '../azioni-rapide/azioni-rapide';
import { InformazioniDatabase } from '../informazioni-database/informazioni-database';

@Component({
  selector: 'app-home',
  imports: [AzioniRapide, InformazioniDatabase],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {}
