package com.example.bookshelf.model;

public class Book {

  private long id;
  private String title;
  private long authorId;
  private int publicationYear;

  public Book() {
  }

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public long getAuthorId() { return authorId; }
  public void setAuthorId(long authorId) { this.authorId = authorId; }

  public int getPublicationYear() { return publicationYear; }
  public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
}
