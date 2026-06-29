package com.example.bikecustomservise.api.model.order;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderItem(
        @Positive
        BigDecimal orderPrice,
        @NonNull
        String orderPremium,
        @NonNull
        String orderType
) {
}
