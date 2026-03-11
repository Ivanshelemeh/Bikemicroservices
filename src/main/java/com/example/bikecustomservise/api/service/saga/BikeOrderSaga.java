package com.example.bikecustomservise.api.service.saga;

import com.example.bikecustomservise.api.exception.OrderRecommendationProcessException;
import com.example.bikecustomservise.api.model.order.integration.OrderRecUpdateFailCommand;
import com.example.bikecustomservise.api.model.order.integration.RecommendationUpdateCommand;
import com.example.bikecustomservise.api.service.order.service.integration.BikeOrderRecommendationProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "${recommendation.events.topic.name}")
public class BikeOrderSaga {

    @Value("${bike-order.events.topic.name}")
    private String bikeOrderTopic;
    @Value("${recommendation.events.fail.topic.name}")
    private String recommendationFailTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BikeOrderRecommendationProcess recommendationProcess;

    @KafkaHandler
    public void handleRecommendationCommand(@Payload RecommendationUpdateCommand updateCommand) {
        try {
            var orderRecommendation = recommendationProcess.processModel(updateCommand);
            kafkaTemplate.send(bikeOrderTopic, String.valueOf(orderRecommendation.processedId()),
                    orderRecommendation);
        } catch (OrderRecommendationProcessException e) {
            log.error(e.getMessage(), e);
            OrderRecUpdateFailCommand failCommand = new OrderRecUpdateFailCommand(
                    updateCommand.recUpdateId(),
                    updateCommand.description()
            );

            kafkaTemplate.send(recommendationFailTopic, String.valueOf(failCommand.recommendationId()),
                    failCommand);

        }


    }

}
