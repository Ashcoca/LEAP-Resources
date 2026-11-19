package com.fidelity.bookdemo.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateBookRequest(

        @NotBlank(message = "is required")
        @Size(max = 255, message = "must be 255 characters or fewer")
        String title,

        @NotBlank(message = "is required")
        @Size(max = 255, message = "must be 255 characters or fewer")
        String author,

        @NotNull(message = "is required")
        @Positive(message = "must be greater than 0")
        Integer pages
) {}