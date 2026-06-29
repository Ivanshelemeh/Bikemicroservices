package com.example.bikecustomservise.api.model.order.integration;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.lang.NonNull;



public record Recommendation(

        String recommendId,
        @NonNull
        String recommendationContent,
        @PositiveOrZero
        Double recommendationRate
) {
}
