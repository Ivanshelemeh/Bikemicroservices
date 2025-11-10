package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.OrderRecommendationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BikeOrderRecommendationController implements BikeOrderRecommendationApi {

    @Override
    public ResponseEntity<OrderRecommendationDTO> getOrderRateInfo(String orderId) {
        return null;
    }
}
