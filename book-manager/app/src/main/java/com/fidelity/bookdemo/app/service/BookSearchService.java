package com.fidelity.bookdemo.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelity.bookdemo.app.client.GoogleBooksClient;
import com.fidelity.bookdemo.app.client.GoogleBooksResponse;
import com.fidelity.bookdemo.app.dto.BookSuggestion;

@Service
public class BookSearchService {

    private final GoogleBooksClient googleBooksClient;

    public BookSearchService(GoogleBooksClient googleBooksClient) {
        this.googleBooksClient = googleBooksClient;
    }

    public List<BookSuggestion> search(String query) {
        GoogleBooksResponse response = googleBooksClient.searchVolumes(query.trim());
        if (response == null || response.items() == null) {
            return List.of();   // no matches
        }
        return response.items().stream()
                .filter(item -> item.volumeInfo() != null && item.volumeInfo().title() != null)
                .map(this::toSuggestion)
                .toList();
    }

    private BookSuggestion toSuggestion(GoogleBooksResponse.Item item) {
        GoogleBooksResponse.VolumeInfo info = item.volumeInfo();

        String author = (info.authors() == null || info.authors().isEmpty())
                ? "Unknown"
                : String.join(", ", info.authors());

        String thumbnail = info.imageLinks() == null ? null : info.imageLinks().thumbnail();
        if (thumbnail != null) {
            thumbnail = thumbnail.replaceFirst("^http://", "https://");  // Google often returns http links
        }

        return new BookSuggestion(item.id(), info.title(), author, info.pageCount(), thumbnail);
    }
}
