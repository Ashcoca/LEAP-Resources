package com.example.bookshelf.model;

public class BookSummary {

  private long id;
  private String title;
  private int publicationYear;
  private String authorName;

  public BookSummary() {
  }

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public int getPublicationYear() { return publicationYear; }
  public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

  public String getAuthorName() { return authorName; }
  public void setAuthorName(String authorName) { this.authorName = authorName; }
}
