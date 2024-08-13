package com.example.bikecustomservise.api.dto;

import org.springframework.lang.NonNull;

public record BikeCustomerUpdateDto(
        @NonNull
        String name,
        @NonNull
        String email
) {
}
