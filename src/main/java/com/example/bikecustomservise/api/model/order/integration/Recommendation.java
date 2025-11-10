package com.example.bikecustomservise.api.model.order.integration;

import org.springframework.lang.NonNull;

import javax.validation.constraints.PositiveOrZero;

public record Recommendation(
        @NonNull
        String recommendationContent,
        @PositiveOrZero
        Double recommendationRate
) {
}
