package com.example.bikecustomservise.api.dto.analyze;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

public record FailedTransactionEvent(
        @NonNull
        Long transactionId,
        @NonNull
        Long customerId,
        @NotBlank
        String ownerTransaction,
        @NotBlank
        String descriptionDetail,
        String period
) {}
