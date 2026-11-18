import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Book } from './book.model';
import { BookService } from './book.service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-books',
  styleUrl: './books.css',
  templateUrl: './books.html',
})
export class Books implements OnInit {
  private fb = inject(FormBuilder);
  private bookService = inject(BookService);

  books = signal<Book[]>([]);
  error = signal<string | null>(null);

  totalCount = computed(() => this.books().length);
  finishedCount = computed(() => this.books().filter(b => b.finished).length);

  form = this.fb.nonNullable.group({
    title: ['', [Validators.required, Validators.maxLength(255)]],
    author: ['', [Validators.required, Validators.maxLength(255)]],
    pages: [1, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getAll().subscribe({
      next: books => this.books.set(books),
      error: () => this.error.set('Could not load books. Is the API running?'),
    });
  }

  addBook(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.bookService.create(this.form.getRawValue()).subscribe({
      next: created => {
        this.books.update(list => [...list, created]);
        this.form.reset();
        this.error.set(null);
      },
      error: err => this.error.set(this.describeError(err)),
    });
  }

  finish(book: Book): void {
    this.bookService.finish(book.id).subscribe({
      next: updated =>
        this.books.update(list => list.map(b => (b.id === updated.id ? updated : b))),
      error: err => this.error.set(this.describeError(err)),
    });
  }

  remove(book: Book): void {
    this.bookService.delete(book.id).subscribe({
      next: () => this.books.update(list => list.filter(b => b.id !== book.id)),
      error: err => this.error.set(this.describeError(err)),
    });
  }

  private describeError(err: any): string {
    console.log(err);
    // The Spring API returns { field: message } for validation errors (see Step 4)
    if (err?.status === 400 && err.error && typeof err.error === 'object') {
      return Object.entries(err.error).map(([f, m]) => `${f}: ${m}`).join(', ');
    }
    return 'Something went wrong. Please try again.';
  }
}