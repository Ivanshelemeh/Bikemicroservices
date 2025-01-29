package com.example.bikecustomservise.api.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record BikeOrderCreateDTO(
        @NonNull
        @NotBlank
        String email,
        @NonNull
        @NotBlank
        String orderName,
        @NonNull
        @Positive
        Double orderCost
) {
}
