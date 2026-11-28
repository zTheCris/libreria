import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LibriService } from '../../libri-service';
import { Libro } from '../../libro';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dettagli-libro',
  imports: [],
  templateUrl: './dettagli-libro.html',
  styleUrl: './dettagli-libro.css',
})
export class DettagliLibro {

  route = inject(ActivatedRoute);
  libriService = inject(LibriService);

  libro = signal<Libro | null>(null);

  constructor() {
    // Recupero l'ISBN dalla URL.
    const isbn = Number(this.route.snapshot.params['id']);

    // Recupero il libro tramite il suo ISBN.
    this.libriService.getByIsbn(isbn).subscribe({
      next: (res: Libro) => {
        this.libro.set(res);
      },
      error: () => {
        Swal.fire({
          title: 'Libro non trovato',
          icon: 'error'
        });
      }
    });
  }
}
