import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: '**',
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Per far si che localStorage venga letto durante il rendering della pagina, (quando accedo alla root),
    // Devo mettere la modalità di render a Client invece che Prerender.
    // Dato che io sto lavorando sul localStorage, invece che usare ad esempio i Cookie,
    // Uso il Client come rendermode.
    renderMode: RenderMode.Client
  }
];
