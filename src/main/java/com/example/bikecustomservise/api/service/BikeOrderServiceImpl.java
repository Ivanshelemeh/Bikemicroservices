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
    public BikeOrderDTO findOrderById(Integer price) {
        if (price < 0) {
            throw new NoSuchFieldException("not such price available");
        }
        BikeOrder bikeOrder = new BikeOrder();
        bikeOrder.setPriceOrder(Double.valueOf(price));
        orderRepository.save(bikeOrder);
        return orderMapper.mapToOrderDto(bikeOrder);
    }

    @Override
    public void deleteByOrderName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("not such name ");
        }
        Optional<BikeOrder> optionalBikeOrder = orderRepository.findAll()
                .stream().filter(order1 -> order1.getNameOrder().equals(name)).findFirst();
        if (!optionalBikeOrder.isPresent()) {
            BikeOrder bikeOrder = new BikeOrder();
            bikeOrder.setNameOrder(name);
            orderRepository.delete(bikeOrder);
        }
        orderRepository.delete(optionalBikeOrder.get());
    }

    @Override
    public BikeOrder saveOrder(@NotNull @Validated BikeOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("not such order exits");
        }
        orderRepository.save(order);
        return order;
    }
}
