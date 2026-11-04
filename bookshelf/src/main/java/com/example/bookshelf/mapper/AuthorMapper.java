package com.example.bookshelf.mapper;

import com.example.bookshelf.model.BookSummary;

import java.util.List;

public interface AuthorMapper {

  List<BookSummary> findBooksByAuthorId(long authorId);
}
