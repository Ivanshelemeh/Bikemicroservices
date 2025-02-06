package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.model.PageRq;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;

public record OrderTypedFindModel(
        @NonNull
        String orderType,
        @Nullable
        String orderName,
        @Positive
        double priceOrderType,
        PageRq req

) {
}
