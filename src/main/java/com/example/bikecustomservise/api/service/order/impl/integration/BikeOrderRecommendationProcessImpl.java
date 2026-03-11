package com.example.bikecustomservise.api.service.order.impl.integration;

import com.example.bikecustomservise.api.exception.OrderRecommendationProcessException;
import com.example.bikecustomservise.api.model.order.integration.OrderRecommendationProcessModel;
import com.example.bikecustomservise.api.model.order.integration.RecommendationUpdateCommand;
import com.example.bikecustomservise.api.service.order.service.integration.BikeOrderRecommendationProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class BikeOrderRecommendationProcessImpl implements BikeOrderRecommendationProcess {

    private static final String ORDER_RECOMMENDATION_VALUE_ERROR = "Recommendation update of order is failing now";

    @Override
    public OrderRecommendationProcessModel processModel(RecommendationUpdateCommand updateCommand) {
        log.info("Processing update recommendation of order ={}", updateCommand.recUpdateId());
        if (ObjectUtils.isEmpty(updateCommand)) {
            log.error("Fail to process recommendation of order");
            throw new OrderRecommendationProcessException(ORDER_RECOMMENDATION_VALUE_ERROR);
        }

        return new OrderRecommendationProcessModel(
                updateCommand.recUpdateId(),
                updateCommand.updateTime(),
                updateCommand.description(),
                updateCommand.orderRang()
        );
    }
}
