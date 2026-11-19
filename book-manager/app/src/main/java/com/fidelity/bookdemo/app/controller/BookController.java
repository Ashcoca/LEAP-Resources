package com.fidelity.bookdemo.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.bookdemo.app.dto.BookSuggestion;
import com.fidelity.bookdemo.app.dto.CreateBookRequest;
import com.fidelity.bookdemo.app.model.Book;
import com.fidelity.bookdemo.app.service.BookSearchService;
import com.fidelity.bookdemo.app.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookSearchService bookSearchService;

    public BookController(BookService bookService, BookSearchService bookSearchService) {
        this.bookService = bookService;
        this.bookSearchService = bookSearchService;
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody CreateBookRequest request) {
        return bookService.createBook(request);
    }

    @PatchMapping("/{id}/finish")
    public Book finish(@PathVariable long id) {
        return bookService.finishBook(id);          // 404 if missing
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bookService.deleteBook(id);                 // 404 if missing
    }

    @GetMapping("/search")
    public List<BookSuggestion> search(@RequestParam @NotBlank @Size(max = 200) String q) {
        return bookSearchService.search(q);
    }
}
