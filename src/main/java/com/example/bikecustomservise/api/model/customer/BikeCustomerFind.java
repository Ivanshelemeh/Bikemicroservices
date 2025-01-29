package com.example.bikecustomservise.api.model.customer;

import com.example.bikecustomservise.api.model.PageRq;

import javax.validation.constraints.Positive;

public record BikeCustomerFind(
        @Positive
        double priceOrder,
        PageRq pageRq
) {
}
