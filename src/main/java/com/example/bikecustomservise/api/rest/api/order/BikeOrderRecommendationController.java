package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.OrderRecommendationDTO;
import com.example.bikecustomservise.api.service.order.service.BikeOrderRecommendationService;
import com.example.bikecustomservise.api.utilit.BikeOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BikeOrderRecommendationController implements BikeOrderRecommendationApi {

    private final BikeOrderRecommendationService recommendationService;
    private final BikeOrderMapper mapper;

    @Override
    public ResponseEntity<OrderRecommendationDTO> getOrderRateInfo(@Valid @NonNull String orderId) {
        final var orderRecommendation = recommendationService.getOrderRecommendation(Integer.valueOf(orderId));
        return ResponseEntity.ok(mapper.mapFromModel(orderRecommendation));
    }

}
