package com.example.bookshelf.controller;

import com.example.bookshelf.mapper.AuthorMapper;
import com.example.bookshelf.mapper.BookMapper;
import com.example.bookshelf.model.Book;
import com.example.bookshelf.model.BookSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookshelfController {

  private final BookMapper bookMapper;
  private final AuthorMapper authorMapper;

  public BookshelfController(BookMapper bookMapper, AuthorMapper authorMapper) {
    this.bookMapper = bookMapper;
    this.authorMapper = authorMapper;
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBook(@PathVariable long id) {
    Book book = bookMapper.findById(id);
    return book == null
        ? ResponseEntity.notFound().build()
        : ResponseEntity.ok(book);
  }

  @GetMapping("/authors/{id}/books")
  public List<BookSummary> getBooksByAuthor(@PathVariable long id) {
    return authorMapper.findBooksByAuthorId(id);
  }
}
