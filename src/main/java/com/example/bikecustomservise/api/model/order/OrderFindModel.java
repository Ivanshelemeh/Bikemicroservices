package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.model.PageRq;

import javax.validation.constraints.Positive;

public record OrderFindModel(
        @Positive
        double priceOrder,
        PageRq pageRq
) {
}
