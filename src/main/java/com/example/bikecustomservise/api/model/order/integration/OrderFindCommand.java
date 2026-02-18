package com.example.bikecustomservise.api.model.order.integration;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.UUID;

public record OrderFindCommand(
        @NonNull
        UUID orderFindId,
        @NonNull
        Instant lastModifyTime
) {

    public static OrderFindCommand resolveFromOrderEntity(BikeOrder order) {
        return new OrderFindCommand(
                UUID.fromString(order.getNameOrder()),
                order.getLastModified()
        );

    }
}
