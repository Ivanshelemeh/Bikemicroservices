package com.example.bikecustomservise.api.model.customer;

import lombok.NonNull;



public record BikeCustomerFindModel(

        @NonNull
        Integer customerId,

        @org.springframework.lang.NonNull
        String customerName
) {
}
