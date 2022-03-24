package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.repos.BikeOrderRepository;
import com.example.bikecustomservise.api.utilit.BikeOrderMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeOrderServiceImpl implements BikeOrderService {

    private final BikeOrderRepository orderRepository;
    private final BikeOrderMapper orderMapper;

    @Autowired
    public BikeOrderServiceImpl(BikeOrderRepository orderRepository, BikeOrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<BikeOrderDTO> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::mapToOrderDto).collect(Collectors.toList());
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
    public void deleteByOrderName(String name) {
        Optional<BikeOrder> optionalBikeOrder = Optional.ofNullable(orderRepository.findAll()
                .stream().filter(order1 -> order1.getNameOrder().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchFieldException("not such name present")));
        orderRepository.delete(optionalBikeOrder.get());
    }

    @Override
    public BikeOrder saveOrder(@NotNull @Validated BikeOrder order) {
        return Optional.ofNullable(orderRepository.save(order))
                .orElseThrow(() -> new IllegalArgumentException("not such valid order exists"));
    }
}
