package com.example.bikecustomservise.api.dto.order;

import org.springframework.lang.NonNull;

import java.util.List;

public record BikeOrderDTO(
        @NonNull
        String orderName,
        @NonNull
        List<OrderItemDTO> itemDTOs


) {
}
