package com.example.bikecustomservise.api.dto.analyze;

import org.springframework.lang.NonNull;

import javax.validation.constraints.PositiveOrZero;

public record AnalyzeEventDto (
        @NonNull
        Long customerId,
        @NonNull
        Long customerTransactions,

        @PositiveOrZero
        Long amountOfOrders
) {
}
