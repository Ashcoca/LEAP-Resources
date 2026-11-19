package com.fidelity.bookdemo.app.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.bookdemo.app.dto.CreateBookRequest;
import com.fidelity.bookdemo.app.exception.BookNotFoundException;
import com.fidelity.bookdemo.app.model.Book;
import com.fidelity.bookdemo.app.repo.BookRepository;

@Service 
public class BookService {
    private final BookRepository bookRepo;

    BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepo.findAll(Sort.by("id"));
    }

    public Book createBook(CreateBookRequest request) {
        Book book = new Book(request.title().trim(), request.author().trim(), request.pages());
        return bookRepo.save(book);
    }

    public Book finishBook(long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.markFinished();
        // No save() needed: inside a transaction, JPA detects the change
        // and issues the UPDATE when the method returns ("dirty checking").
        return book;
    }

    public void deleteBook(long id) {
        if (!bookRepo.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepo.deleteById(id);
    }
}
