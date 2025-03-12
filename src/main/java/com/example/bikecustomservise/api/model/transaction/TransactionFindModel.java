package com.example.bikecustomservise.api.model.transaction;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public record TransactionFindModel(
        @NonNull
        @Positive
        Integer customerId,
        LocalDateTime startPeriod,
        LocalDateTime endPeriod
) {
}
