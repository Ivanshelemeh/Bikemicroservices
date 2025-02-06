package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.model.order.OrderFindModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.repos.order.OrderPremium;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeOrderPremiumServiceImpl implements BikeOrderPremiumService {

    private final OrderPremium orderPremium;

    @Override
    public Set<OrderModel> findPremiumOrder(@NotNull final OrderFindModel findModel) {
        return orderPremium.findPremiumOrder(findModel.priceOrder(), findModel.pageRq().size(), findModel.pageRq().getPage())
                .stream()
                .map(this::mapFromOrderEntity)
                .collect(Collectors.toSet());
    }

    private OrderModel mapFromOrderEntity(@NonNull final BikeOrder bikeOrder) {
        return new OrderModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getPriceOrder()
        );

    }
}
