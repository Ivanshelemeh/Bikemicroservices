package com.example.bikecustomservise.api.model.order.integration;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public record RecommendationUpdateCommand(
        @NonNull
        UUID recUpdateId,
        @NonNull
        Instant updateTime,
        @Nullable
        String description
) {
}
