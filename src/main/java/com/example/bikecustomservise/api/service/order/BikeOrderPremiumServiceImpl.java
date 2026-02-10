package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.entities.BikeOrderItems;
import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.order.OrderFindNamesModel;
import com.example.bikecustomservise.api.model.order.OrderItem;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeOrderPremiumServiceImpl implements BikeOrderPremiumService {

    private final BikeOrderRepository orderRepository;

    @Value("${premium.markUp.price}")
    private BigDecimal premiumMarkUp;

    @Override
    public List<OrderModel> findPremiumOrder(@NotNull final OrderFindNamesModel findModel) {
        log.debug("finding premium order");
        return orderRepository.findPremiumOrder(PageRequest.of(
                        findModel.pageRq().getPage(),
                        findModel.pageRq().getSize()
                ))
                .stream()
                .map(this::mapFromOrderEntity)
                .toList();
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public OrderModel getOnePremiumModelByName(@NotBlank String orderType, @Positive Double price) {
        log.info("Fetch premium order ={}", price);
        final var currOrder = orderRepository.findCurrentOrderType(orderType, price)
                .orElseThrow(() -> new ServiceProccessingException(ApplicationErrorEnum.ORDER_NOT_FOUND));

        var premiumItems = currOrder.getOrderItems()
                .stream()
                .filter(BikeOrderPremiumServiceImpl::isPremium)
                .map(this::mapToPremiumOrderItem)
                .toList();

        return new OrderModel(currOrder.getNameOrder(), premiumItems);

    }

    private OrderModel mapFromOrderEntity(@NonNull final BikeOrder bikeOrder) {
        return new OrderModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getOrderItems()
                        .stream()
                        .map(this::mapFromBikeOrderItem)
                        .toList()
        );

    }

    private OrderItem mapFromBikeOrderItem(@NonNull BikeOrderItems items) {
        return new OrderItem(
                items.getPrice(),
                items.getPremiumOrder().name(),
                items.getOrderType().name()
        );
    }

    private OrderItem mapToPremiumOrderItem(@NonNull BikeOrderItems item) {
        return new OrderItem(
                item.getPrice().multiply(premiumMarkUp),
                item.getPremiumOrder().name(),
                item.getOrderType().name()
        );
    }

    private static boolean isPremium(BikeOrderItems item) {
        return BikeOrderItems.PremiumOrder.TRUE.equals(item.getPremiumOrder());
    }
}
