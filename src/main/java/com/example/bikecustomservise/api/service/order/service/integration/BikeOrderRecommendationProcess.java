package com.example.bikecustomservise.api.service.order.service.integration;

import com.example.bikecustomservise.api.model.order.integration.OrderRecommendationProcessModel;
import com.example.bikecustomservise.api.model.order.integration.RecommendationUpdateCommand;

public interface BikeOrderRecommendationProcess {

    OrderRecommendationProcessModel processModel(RecommendationUpdateCommand updateCommand);
}
