package com.example.bikecustomservise.api.dto.order;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

public record BikeOrderDTO(
        @NonNull
        String orderName,
        @Positive
        double priceOrder
) {
}
