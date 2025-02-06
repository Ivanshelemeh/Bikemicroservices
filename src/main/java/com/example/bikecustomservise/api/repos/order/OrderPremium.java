package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;

import java.util.List;

public interface OrderPremium {

    List<BikeOrder> findPremiumOrder(double priceOrder,int pageSize, int page);
}
