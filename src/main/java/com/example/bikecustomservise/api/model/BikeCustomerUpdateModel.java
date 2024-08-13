package com.example.bikecustomservise.api.model;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

public record BikeCustomerUpdateModel(
        @CustomNameValid
        @NonNull
        String name,
        @NonNull
        String email
) {
}
