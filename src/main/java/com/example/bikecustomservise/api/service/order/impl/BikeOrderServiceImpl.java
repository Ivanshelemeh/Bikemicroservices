package com.example.bikecustomservise.api.service.order.impl;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.example.bikecustomservise.api.config.BlazePersistenceConfig;
import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.entities.BikeOrderItems;
import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.order.OrderCreateModel;
import com.example.bikecustomservise.api.model.order.OrderFindPricesModel;
import com.example.bikecustomservise.api.model.order.OrderItem;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import com.example.bikecustomservise.api.service.order.service.BikeOrderService;
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

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.ORDER_ALREADY_EXISTS;

@Service
@Slf4j
@RequiredArgsConstructor
public class BikeOrderServiceImpl implements BikeOrderService {

    private final BikeOrderRepository orderRepository;
    private final BikeOrderMapper orderMapper;
    private final BlazePersistenceConfig config;

    @Override
    @Cacheable(value = "cacheConf", unless = "#result == null || #result.isEmpty()")
    public PageRs<OrderModel> find(@NotNull final OrderFindPricesModel findModel) {
        CriteriaBuilderFactory builderFactory = config.createCriteriaBuilderFactory();
        CriteriaBuilder<BikeOrder> cb = builderFactory.create(config.entityManagerFactory(), BikeOrder.class, "bo");
        if (!findModel.orderNames().isEmpty()) {
            cb.where("bo.product_name").eq(findModel.orderNames());
        }
        cb.orderByAsc("bo.created_at").orderByAsc("bo.id");


        final Page<BikeOrder> orderPages = orderRepository.findOrders(
                cb,
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
        log.debug("Deleting order with name{}", name);
        orderRepository.deleteBikeOrder(name);
        log.info("Deleted order success with");

    }

    @Override
    @Transactional
    public OrderModel saveOrder(@NonNull @Validated final OrderCreateModel model)
            throws ServiceProccessingException {
        log.debug("Saving order with name{}", model.orderName());
        if (orderRepository.existsBikeOrderByNameOrder(model.orderName())) {
            throw new ServiceProccessingException(ORDER_ALREADY_EXISTS);
        }
        final var bikeOrder = orderRepository.save(orderMapper.mapFromModel(model));
        log.info("Create order with customer email{}", model.customerEmail());
        return mapFromOrderEntity(bikeOrder);

    }

    private OrderModel mapFromOrderEntity(@NonNull final BikeOrder bikeOrder) {
        return new OrderModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getOrderItems()
                        .stream()
                        .map(this::mapFromBikeOrderItem)
                        .toList());

    }

    private OrderItem mapFromBikeOrderItem(@NonNull BikeOrderItems items) {
        return new OrderItem(
                items.getPrice(),
                items.getPremiumOrder().name(),

                items.getOrderType().name()
        );

    }
}
