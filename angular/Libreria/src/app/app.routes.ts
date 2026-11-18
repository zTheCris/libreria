import { Home } from './../home/home';
import { CaseEditriciComponent } from './../case-editrici-component/case-editrici-component';
import { CopieComponent } from './../copie-component/copie-component';
import { AutoriComponent } from './../autori-component/autori-component';
import { LibriComponent } from './../libri-component/libri-component';
import { Routes } from '@angular/router';
import { App } from './app';
import { GeneriComponent } from '../generi-component/generi-component';

export const routes: Routes = [
  {
    path: '',
    component: Home

  },
  {
    path: 'libri',
    component: LibriComponent
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
  }

];
