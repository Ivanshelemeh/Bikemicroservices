package com.example.bikecustomservise.api.model.customer;

import com.example.bikecustomservise.api.model.PageRq;

import javax.validation.constraints.NotNull;

public record BikeCustomerPremiumFind(
        @NotNull
        PageRq request
) {
}
