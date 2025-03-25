package com.example.bikecustomservise.api.model.order;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

public record OrderTypedFindModel(
        @NonNull
        String orderType,
        @Positive
        double priceOrderType

) {
}
