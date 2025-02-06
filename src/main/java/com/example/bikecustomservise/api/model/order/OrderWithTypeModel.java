package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

public record OrderWithTypeModel(
        @CustomNameValid
        @NonNull
        String orderName,
        @Positive
        @NonNull
        double orderCost,
        @NonNull
        String orderType
) {
}
