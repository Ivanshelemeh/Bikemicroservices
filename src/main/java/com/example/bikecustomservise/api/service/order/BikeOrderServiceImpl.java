package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.order.OrderCreateModel;
import com.example.bikecustomservise.api.model.order.OrderFindModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import com.example.bikecustomservise.api.utilit.BikeOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.USER_EMAIL_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class BikeOrderServiceImpl implements BikeOrderService {

    private final BikeOrderRepository orderRepository;
    private final BikeOrderMapper orderMapper;

    @Override
    @Cacheable(value = "cacheConf", unless = "#result.shares< 100")
    public PageRs<OrderModel> find(@NotNull final OrderFindModel findModel) {
        final Page<BikeOrder> orderPages = orderRepository.findOrders(
                findModel.priceOrder(),
                PageRequest.of(
                        findModel.pageRq().getPage(),
                        findModel.pageRq().getSize()
                )
        );
        return new PageRs<>(orderPages.getContent()
                .stream()
                .map(this::mapFromOrderEntity)
                .toList(),
                orderPages.getSize(),
                orderPages.hasNext(),
                orderPages.getNumber(),
                Math.toIntExact(orderPages.getTotalElements()));
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public OrderModel findByOrderId(@NonNull final Integer id) {
        final var order = orderRepository.findBikeOrderById(id)
                .orElseThrow(() -> new ServiceProccessingException(ApplicationErrorEnum.ORDER_NOT_FOUND));
        return mapFromOrderEntity(order);
    }

    @SneakyThrows
    @Override
    @CacheEvict(value = "cacheConf", key = "#name")
    public void deleteByOrderName(@NonNull final String name) {
        orderRepository.deleteBikeOrder(name);

    }

    @Override
    @Transactional
    public BikeOrder saveOrder(@NonNull @Validated final OrderCreateModel model) throws ServiceProccessingException {
        final var bikeOrder = orderRepository.save(orderMapper.mapFromModel(model));
        final Set<String> emails = bikeOrder.getCustomers()
                .stream()
                .map(BikeCustomer::getEmail)
                .collect(Collectors.toSet());
        if (!emails.contains(model.customerEmail())) {
            throw new ServiceProccessingException(USER_EMAIL_NOT_FOUND);
        }
        return bikeOrder;

    }

    private OrderModel mapFromOrderEntity(@NonNull final BikeOrder bikeOrder) {
        return new OrderModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getPriceOrder()
        );

    }
}
