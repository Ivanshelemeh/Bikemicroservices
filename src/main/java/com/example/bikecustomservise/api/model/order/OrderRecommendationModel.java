package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

import javax.validation.constraints.PositiveOrZero;

public record OrderRecommendationModel(
        @NonNull
        @CustomNameValid
        String orderName,

        @NonNull
        String content,

        @PositiveOrZero
        Double orderRate
) {
}
