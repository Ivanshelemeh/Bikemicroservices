package com.example.bikecustomservise.api.model.order.integration;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

public record OrderRecUpdateFailCommand(
        @NonNull
        UUID recommendationId,
        @Nullable
        String recommendationDescription

) {
}
