package com.example.bikecustomservise.api.dto.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record OrderRecommendationDTO(
        @NotNull
        String orderName,
        @NotNull
        String content,

        @PositiveOrZero
        Double rate
) {
}
