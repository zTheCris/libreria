import { Component, signal } from '@angular/core';
import { Sidebar } from '../sidebar/sidebar';
import { RouterOutlet, RouterLinkWithHref } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [Sidebar, RouterOutlet, RouterLinkWithHref],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Libreria');
}
