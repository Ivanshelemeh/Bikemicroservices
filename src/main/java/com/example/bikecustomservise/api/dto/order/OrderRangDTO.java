package com.example.bikecustomservise.api.dto.order;

import org.springframework.lang.Nullable;

import javax.validation.constraints.PositiveOrZero;

public record OrderRangDTO(
        @Nullable
        String orderRangDescription,
        @PositiveOrZero
        double OrderRangNumber
) {
}
