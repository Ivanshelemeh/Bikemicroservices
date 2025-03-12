package com.example.bikecustomservise.api.model.transaction;

import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;

import java.time.LocalDateTime;

public record TransactionalModel(
        LocalDateTime period,
        BikeCustomerModel customer
) {
}
