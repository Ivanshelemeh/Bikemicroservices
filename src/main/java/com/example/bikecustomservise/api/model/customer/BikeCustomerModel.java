package com.example.bikecustomservise.api.model.customer;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.lang.NonNull;

public record BikeCustomerModel(
        @CustomNameValid
        String nameCustomer,
        @NonNull
        String customerEmail,
        @NonNull
        String customerPassword
) {
}
