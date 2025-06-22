package com.example.bikecustomservise.api.model.transaction;

import com.example.bikecustomservise.api.model.PageRq;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record TransactionalPeriodFindModel(
        @NonNull
        LocalDateTime startPeriod,
        @NonNull
        LocalDateTime endPeriod,
        @NonNull
        String status,
        PageRq request
) {
}
