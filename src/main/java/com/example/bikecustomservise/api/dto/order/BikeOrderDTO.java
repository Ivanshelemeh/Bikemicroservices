package com.example.bikecustomservise.api.dto.order;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

public record BikeOrderDTO(
        @NonNull
        @CustomNameValid
        String orderName,
        @Positive
        double priceOrder
) {
}
