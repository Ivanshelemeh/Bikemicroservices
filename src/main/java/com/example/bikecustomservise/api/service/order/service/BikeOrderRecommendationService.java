package com.example.bikecustomservise.api.service.order.service;

import com.example.bikecustomservise.api.model.order.OrderRecommendationModel;
import org.springframework.lang.NonNull;

public interface BikeOrderRecommendationService {

    OrderRecommendationModel getOrderRecommendation(@NonNull Integer orderId);
}
