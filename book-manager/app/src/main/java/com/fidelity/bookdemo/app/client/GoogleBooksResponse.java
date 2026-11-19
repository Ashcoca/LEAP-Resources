package com.fidelity.bookdemo.app.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleBooksResponse(int totalItems, List<Item> items) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(String id, VolumeInfo volumeInfo) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VolumeInfo(String title, List<String> authors, Integer pageCount, ImageLinks imageLinks) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ImageLinks(String thumbnail) {}
}