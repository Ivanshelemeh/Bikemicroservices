package com.example.bikecustomservise.api.model.order;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
