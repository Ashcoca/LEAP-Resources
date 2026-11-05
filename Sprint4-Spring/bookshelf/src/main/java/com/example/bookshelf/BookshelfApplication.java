package com.example.bookshelf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bookshelf.mapper")
public class BookshelfApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookshelfApplication.class, args);
  }
}
