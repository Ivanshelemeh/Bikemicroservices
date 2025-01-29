package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

public record OrderModel(
        @NonNull
        @CustomNameValid
        String orderName,
        @Positive
        @NonNull
        double orderPrice
) {
}
