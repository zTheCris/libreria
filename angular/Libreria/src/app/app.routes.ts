import { MostraCatalogo } from './../mostra-catalogo/mostra-catalogo';
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
    component: AutoriComponent
  },

  {
    path: "generi",
    component: GeneriComponent
  },

  {
    path: "copie",
    component: CopieComponent
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
