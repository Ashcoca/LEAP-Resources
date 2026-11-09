package com.example.bookshelf.mapper;

import com.example.bookshelf.model.Book;
import org.apache.ibatis.annotations.Select;

public interface BookMapper {

  @Select("SELECT id, title, author_id, publication_year "
        + "FROM books WHERE id = #{id}")
  Book findById(long id);
}
