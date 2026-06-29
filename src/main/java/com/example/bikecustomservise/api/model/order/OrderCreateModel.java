package com.example.bikecustomservise.api.model.order;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderCreateModel(
        @NonNull
        @NotBlank
        String orderName,
        @NonNull
        @NotBlank
        String customerEmail,
        @NonNull
        @Positive
        Double priceOrder
) {
}
