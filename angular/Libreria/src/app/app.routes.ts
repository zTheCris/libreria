import { DettagliLibro } from './../libri-component/dettagli-libro/dettagli-libro';
import { MostraAutori } from './../autori-component/mostra-autori/mostra-autori';
import { MostraCatalogo } from '../libri-component/mostra-catalogo/mostra-catalogo';
import { Home } from './../home/home';
import { CaseEditriciComponent } from './../case-editrici-component/case-editrici-component';
import { CopieComponent } from './../copie-component/copie-component';
import { AutoriComponent } from './../autori-component/autori-component';
import { LibriComponent } from './../libri-component/libri-component';
import { Routes } from '@angular/router';
import { GeneriComponent } from '../generi-component/generi-component';
import { InformazioniDatabase } from '../informazioni-database/informazioni-database';
import { AggiungiLibro } from '../libri-component/aggiungi-libro/aggiungi-libro';
import { LoginComponent } from '../login-component/login-component';
import { authGuard } from '../auth-guard';
import { ModificaLibro } from '../libri-component/modifica-libro/modifica-libro';
import { AddAutore } from '../autori-component/add-autore/add-autore';
import { ModificaAutore } from '../autori-component/modifica-autore/modifica-autore';
import { MostraCopie } from '../copie-component/mostra-copie/mostra-copie';

export const routes: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    component: Home
  },

  {
    path: 'libri',
    component: LibriComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        // Per mettere come default uno dei component relativi a libro, si usa il redirectTo.
        redirectTo: 'mostracatalogo',
        // Per dire che deve essere ESATTAMENTE /libri/mostracatalogo, lo definisco con pathMatch: full
        pathMatch: 'full'
      },
      {
        path:'id/:id',
        component: DettagliLibro
      },
      {
        path: 'addLibro',
        component: AggiungiLibro
      },
      {
        path: 'mostracatalogo',
        component: MostraCatalogo
      },
      {
        path: 'modificalibro/:id',
        component: ModificaLibro
      },
      // Due modi per accedere alla modifica libro: se non passo l'id,
      // Il costruttore di modifica lancia Swal.
      // Altrimenti, chiama getByIsbn in base all'id nella url (sopra)
      {
        path: 'modificalibro',
        component: ModificaLibro
      }
    ]
  },

  {
    path: "autori",
    component: AutoriComponent,
    canActivate: [authGuard],
    children: [
      {
        path:'',
        // Stesso comportamento di Libro
        redirectTo: 'mostraautori',
        pathMatch: 'full'
      },
      {
        path: 'mostraautori',
        component: MostraAutori
      },
      {
        path:'addAutore',
        component: AddAutore
      },
      {
        path: 'modificaautore',
        component: ModificaAutore
      }
    ]
  },

  {
    path: "generi",
    component: GeneriComponent,
    canActivate: [authGuard]
  },

  {
    path: "copie",
    component: CopieComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'mostracopie',
        pathMatch: 'full'
      },
      {
        path: 'mostracopie',
        component: MostraCopie
      }
    ]
  },

  {
    path: "case",
    component:CaseEditriciComponent
  },

  {
    path: "login",
    component: LoginComponent
  }

];
