package com.fidelity.bookdemo.app.dto;

public record BookSuggestion(
        String googleId,
        String title,
        String author,
        Integer pages,        // null when Google has no page count
        String thumbnailUrl   // null when Google has no cover image
) {}