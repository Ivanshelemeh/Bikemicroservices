package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public record OrderRecommendationModel(

        @NonNull
        @NotBlank
        String recommendId,
        @NonNull
        @CustomNameValid
        String orderName,

        @Nullable
        String content,

        @PositiveOrZero
        Double orderRate
) {
}
