package com.example.bikecustomservise.api.dto.order;

public record BikeOrderWithTypeDTO(
        String orderName,
        double priceOrder,
        String orderType
) {
}
