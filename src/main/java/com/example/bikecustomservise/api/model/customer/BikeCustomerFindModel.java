package com.example.bikecustomservise.api.model.customer;

import lombok.NonNull;

import javax.validation.constraints.Positive;

public record BikeCustomerFindModel(
        @Positive
        @NonNull
        Integer customerId,

        @org.springframework.lang.NonNull
        String customerName
) {
}
