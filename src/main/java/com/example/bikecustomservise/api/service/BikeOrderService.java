package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;

import java.util.List;

public interface BikeOrderService {


    List<BikeOrderDTO> findAllOrders();

    BikeOrderDTO findOrderByPrice(Double price);

    void deleteByOrderName(String name);

    BikeOrder saveOrder(BikeOrder order);
}
