package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.model.order.OrderRecommendationModel;
import com.example.bikecustomservise.api.model.order.integration.Recommendation;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
public class BikeOrderRecommendationServiceImpl implements BikeOrderRecommendationService {

    private static final String RECOMM_HOST = "localhost";
    private static final String RECOMM_PORT = "8013";
    private static final String RECOMM_SOURCE_URL = "/rest/api/v1/recommendation/";
    private static final String RECOMM_URL = "http://" + RECOMM_HOST + ":" + RECOMM_PORT + RECOMM_SOURCE_URL;
    private final BikeOrderRepository bikeOrderRepository;
    private final WebClient webclient;

    @Autowired
    public BikeOrderRecommendationServiceImpl(BikeOrderRepository bikeOrderRepository, WebClient.Builder webclient) {
        this.bikeOrderRepository = bikeOrderRepository;
        this.webclient = webclient.build();
    }


    @Override
    public OrderRecommendationModel getOrderRecommendation(@NotNull String orderId) {
        final var url = RECOMM_URL + orderId;
        final var intOrderId = Integer.parseInt(orderId);
        final var order = bikeOrderRepository.findBikeOrderById(intOrderId)
                .orElseThrow();

        log.debug("Request to recommendation service to fetch recommendation ");
        final var recommendation = webclient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Recommendation.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid recommendation recieved.")))
                .map(rec -> new Recommendation(
                        rec.recommendId(),
                        rec.recommendationContent(),
                        rec.recommendationRate()
                ))
                .block();

        return new OrderRecommendationModel(
                order.getNameOrder(),
                recommendation.recommendId(),
                recommendation.recommendationContent(),
                recommendation.recommendationRate()
        );


    }


}
