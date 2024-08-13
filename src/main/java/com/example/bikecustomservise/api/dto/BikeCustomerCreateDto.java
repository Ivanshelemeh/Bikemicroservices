package com.example.bikecustomservise.api.dto;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

public record BikeCustomerCreateDto(
        @CustomNameValid
        @NonNull
        String nickName,
        @NonNull
        String customerEmail
) {
}
