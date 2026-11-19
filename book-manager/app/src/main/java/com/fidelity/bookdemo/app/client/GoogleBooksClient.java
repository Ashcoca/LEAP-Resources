package com.fidelity.bookdemo.app.client;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.fidelity.bookdemo.app.exception.GoogleBooksUnavailableException;

@Component
public class GoogleBooksClient {

    private static final Logger log = LoggerFactory.getLogger(GoogleBooksClient.class);

    private final RestClient restClient;
    private final GoogleBooksProperties properties;

    // BUILD OUT THE REQUEST FACTORY, SET YOU API KEYS AND YOUR BASE URL
    // BASE URL AND API KEYS ARE DEFINED IN YOUR application.properties
    public GoogleBooksClient(GoogleBooksProperties properties) {
        this.properties = properties;

        // Fail fast instead of hanging the request if Google is slow
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(3));
        requestFactory.setReadTimeout(Duration.ofSeconds(5));

        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .build();
    }

    public GoogleBooksResponse searchVolumes(String query) {
        if (properties.apiKey() == null || properties.apiKey().isBlank()) {
            throw new GoogleBooksUnavailableException(
                    "Google Books API key is not configured. Set GOOGLE_BOOKS_API_KEY.");
        }

        try {
            // {q}, {max} and {key} are URI variables: RestClient URL-encodes each value safely
            return restClient.get()
                    .uri("/volumes?q={q}&maxResults={max}&printType=books&key={key}",
                            query, properties.maxResults(), properties.apiKey())
                    .retrieve()
                    .body(GoogleBooksResponse.class);
        } catch (RestClientResponseException ex) {
            // Google answered with an error (e.g. 400 bad key, 403 API not enabled, 429 quota)
            log.warn("Google Books returned HTTP {}", ex.getStatusCode().value());
            throw new GoogleBooksUnavailableException("Google Books request failed");
        } catch (RestClientException ex) {
            // Network problem or timeout. Don't log ex.getMessage(): it can contain the URL, and the URL contains the key.
            log.warn("Could not reach Google Books ({})", ex.getClass().getSimpleName());
            throw new GoogleBooksUnavailableException("Google Books is unreachable");
        }
    }
}