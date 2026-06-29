package com.example.bikecustomservise.api.service.order.impl;

import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.order.OrderRecommendationModel;
import com.example.bikecustomservise.api.model.order.integration.Recommendation;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import com.example.bikecustomservise.api.service.order.service.BikeOrderRecommendationService;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class BikeOrderRecommendationServiceImpl implements BikeOrderRecommendationService {

    @Value("${services.recommendation.base.url}")
    private String recommendationUrl;
    private final BikeOrderRepository bikeOrderRepository;
    private final RestClient restClient;


    @Autowired
    public BikeOrderRecommendationServiceImpl(BikeOrderRepository bikeOrderRepository, RestClient.Builder webclient) {
        this.bikeOrderRepository = bikeOrderRepository;
        this.restClient = webclient.build();
    }


    @SneakyThrows
    @Override
    public OrderRecommendationModel getOrderRecommendation(@NotNull Integer orderId) {
        final var order = bikeOrderRepository.findBikeOrderById(orderId)
                .orElseThrow(() -> new ServiceProccessingException(ApplicationErrorEnum.ORDER_RECOMMENDATION_FAIL));

        log.debug("Request to recommendation service to fetch recommendations");
        var rec = getRecommendationByUrl(recommendationUrl);
        return new OrderRecommendationModel(
                rec.recommendId(),
                order.getNameOrder(),
                rec.recommendationContent(),
                rec.recommendationRate()
        );

    }

    @CircuitBreaker(retryFor = TimeoutException.class, maxAttemptsExpression ="300")
    private Recommendation getRecommendationByUrl(String inputUrl) {
        var recommendation = restClient
                .get()
                .uri(inputUrl)
                .retrieve()
                .body(Recommendation.class);
        if (ObjectUtils.isEmpty(recommendation)) {
            log.error("Recommendation request fails by url{}", recommendationUrl);
            throw new IllegalStateException("Requesting recommendation fast fails");
        }
        return recommendation;
    }


}
