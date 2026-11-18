import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Books } from './books/books';

@Component({
  imports: [RouterOutlet, Books],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('book-manager-ui');
}