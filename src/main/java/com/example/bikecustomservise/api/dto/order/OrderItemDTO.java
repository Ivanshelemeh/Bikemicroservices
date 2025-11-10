package com.example.bikecustomservise.api.dto.order;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderItemDTO(
        @Positive
        BigDecimal orderPrice,
        @NonNull
        String orderPremium,
        @NonNull
        String orderType
) {
}
