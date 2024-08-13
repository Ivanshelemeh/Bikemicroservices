package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.annotation.AsyncRunnerAnnotation;
import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.repos.BikeOrderRepository;
import com.example.bikecustomservise.api.utilit.BikeOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BikeOrderServiceImpl implements BikeOrderService {

    private final BikeOrderRepository orderRepository;
    private final BikeOrderMapper orderMapper;

    @Override
    @Cacheable(value = "cacheConf", unless = "#result.shares<100")
    public List<BikeOrderDTO> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::mapToOrderDto).toList();
    }

    @Override
    public BikeOrder findByOrderId(Integer id) {
        return orderRepository.findBikeOrderById(id)
                .orElseGet(BikeOrder::new);
    }

    @Override
    @SneakyThrows
    public BikeOrderDTO findOrderByPrice(Double price) {
        if (price < 0) {
            throw new NoSuchFieldException("not such price available");
        }
        BikeOrder bikeOrder = new BikeOrder();
        bikeOrder.setPriceOrder(price);
        orderRepository.save(bikeOrder);
        return orderMapper.mapToOrderDto(bikeOrder);
    }

    @SneakyThrows
    @Override
    @CacheEvict(value = "cacheConf", key = "#name")
    public void deleteByOrderName(String name) {
        final var bikeOrder = orderRepository.findAll()
                .stream()
                .filter(order1 -> order1.getNameOrder().equals(name))
                .max(Comparator.comparingDouble(BikeOrder::getPriceOrder))
                .orElseThrow(() -> new NoSuchFieldException("not such name present"));
        orderRepository.delete(bikeOrder);
    }

    @Override
    @AsyncRunnerAnnotation
    public BikeOrder saveOrder(@NotNull @Validated BikeOrder order) {
        return Optional.ofNullable(orderRepository.save(order))
                .orElseThrow(() -> new IllegalArgumentException("not such valid order exists"));
    }
}
