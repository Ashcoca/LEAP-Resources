package com.fidelity.bookdemo.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fidelity.bookdemo.app.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
}
