package com.example.bikecustomservise.api.model.transaction;

import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;



public record TransactionFindModel(
        @NonNull
        @Positive
        Integer customerId
) {
}
