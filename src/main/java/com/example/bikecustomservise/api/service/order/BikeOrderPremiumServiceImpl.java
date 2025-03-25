package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.model.order.OrderFindNamesModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeOrderPremiumServiceImpl implements BikeOrderPremiumService {

    private final BikeOrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> findPremiumOrder(@NotNull final OrderFindNamesModel findModel) {
        log.debug("finding premium order");
        return orderRepository.findPremiumOrder(PageRequest.of(
                        findModel.pageRq().getSize(),
                        findModel.pageRq().getPage()
                ))
                .stream()
                .map(this::mapFromOrderEntity)
                .toList();
    }

    private OrderModel mapFromOrderEntity(@NonNull final BikeOrder bikeOrder) {
        return new OrderModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getPriceOrder()
        );

    }
}
