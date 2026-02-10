package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.order.OrderRecommendationModel;
import com.example.bikecustomservise.api.model.order.integration.Recommendation;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import com.example.bikecustomservise.api.utilit.RecommendationServiceProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
public class BikeOrderRecommendationServiceImpl implements BikeOrderRecommendationService {

    private final BikeOrderRepository bikeOrderRepository;
    private final WebClient webclient;
    private final RecommendationServiceProperties properties;

    @Autowired
    public BikeOrderRecommendationServiceImpl(BikeOrderRepository bikeOrderRepository, WebClient.Builder webclient,
                                              RecommendationServiceProperties properties) {
        this.bikeOrderRepository = bikeOrderRepository;
        this.webclient = webclient.build();
        this.properties = properties;
    }


    @SneakyThrows
    @Override
    public OrderRecommendationModel getOrderRecommendation(@NotNull Integer orderId) {
        final var order = bikeOrderRepository.findBikeOrderById(orderId)
                .orElseThrow(() -> new ServiceProccessingException(ApplicationErrorEnum.ORDER_RECOMMENDATION_FAIL));

        log.debug("Request to recommendation service to fetch recommendation ");
        final var recommendation = webclient
                .get()
                .uri(properties.baseUrl())
                .retrieve()
                .bodyToMono(Recommendation.class)
                .timeout(properties.timeOut())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid recommendation recieved.")))
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Recommendation has not responded"));


        return new OrderRecommendationModel(
                recommendation.recommendId(),
                order.getNameOrder(),
                recommendation.recommendationContent(),
                recommendation.recommendationRate()
        );


    }


}
