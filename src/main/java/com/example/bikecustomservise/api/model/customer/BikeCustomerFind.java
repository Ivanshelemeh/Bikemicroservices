package com.example.bikecustomservise.api.model.customer;

import com.example.bikecustomservise.api.model.PageRq;

import jakarta.validation.constraints.Positive;

public record BikeCustomerFind(
        @Positive
        double priceOrder,
        PageRq pageRq
) {
}
