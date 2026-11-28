import { Component, inject, OnInit, DestroyRef } from '@angular/core';
import { AutoriService } from '../autori-service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Autore } from '../autore';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-autori-component',
  imports: [RouterOutlet],
  templateUrl: './autori-component.html',
  styleUrl: './autori-component.css',
})
export class AutoriComponent {
}
