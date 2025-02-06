package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;

import java.util.List;

public interface BikeOrderWithOrderType {

    List<BikeOrder> findBikeOrdersWithCurrentType(double priceOrder,
                                                  String orderType,
                                                  int pageOrder,
                                                  int sizeOrder);
}
