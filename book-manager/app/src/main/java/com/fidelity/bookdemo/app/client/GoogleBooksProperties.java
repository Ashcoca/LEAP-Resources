package com.fidelity.bookdemo.app.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.books")
public record GoogleBooksProperties(String baseUrl, String apiKey, int maxResults) {}