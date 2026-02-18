package com.example.bikecustomservise.api.service.saga;

import com.example.bikecustomservise.api.model.order.integration.OrderRecommendationCommand;
import com.example.bikecustomservise.api.model.order.integration.RecommendationUpdateCommand;
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
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaHandler
    public void handleRecommendationCommand(@Payload RecommendationUpdateCommand updateCommand) {
        var orderRecommendation = new OrderRecommendationCommand(
                updateCommand.recUpdateId(),
                updateCommand.updateTime(),
                updateCommand.description()
        );

        kafkaTemplate.send(bikeOrderTopic, String.valueOf(orderRecommendation.recommendationId()),
                orderRecommendation);


    }

}
