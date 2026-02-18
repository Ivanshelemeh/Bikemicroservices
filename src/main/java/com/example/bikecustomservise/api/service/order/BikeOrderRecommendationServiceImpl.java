package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.order.OrderRecommendationModel;
import com.example.bikecustomservise.api.model.order.integration.Recommendation;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Service
@Slf4j
public class BikeOrderRecommendationServiceImpl implements BikeOrderRecommendationService {

    @Value("${services.recommendation.base.url}")
    private String recommendationUrl;
    @Value("${services.recommendation.timeout}")
    private Long recommendationTimeout;
    private final BikeOrderRepository bikeOrderRepository;
    private final WebClient webclient;


    @Autowired
    public BikeOrderRecommendationServiceImpl(BikeOrderRepository bikeOrderRepository, WebClient.Builder webclient) {
        this.bikeOrderRepository = bikeOrderRepository;
        this.webclient = webclient.build();
    }


    @SneakyThrows
    @Override
    public OrderRecommendationModel getOrderRecommendation(@NotNull Integer orderId) {
        final var order = bikeOrderRepository.findBikeOrderById(orderId)
                .orElseThrow(() -> new ServiceProccessingException(ApplicationErrorEnum.ORDER_RECOMMENDATION_FAIL));

        log.debug("Request to recommendation service to fetch recommendations");
        final var recommendation = webclient
                .get()
                .uri(recommendationUrl)
                .retrieve()
                .bodyToMono(Recommendation.class)
                .timeout(Duration.ofSeconds(recommendationTimeout))
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
