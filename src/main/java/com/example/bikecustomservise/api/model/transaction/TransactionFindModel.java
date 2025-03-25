package com.example.bikecustomservise.api.model.transaction;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

public record TransactionFindModel(
        @NonNull
        @Positive
        Integer customerId
) {
}
