package com.example.bikecustomservise.api.service.order.service;

import com.example.bikecustomservise.api.model.order.OrderFindNamesModel;
import com.example.bikecustomservise.api.model.order.OrderModel;

import java.util.List;

public interface BikeOrderPremiumService {

    List<OrderModel> findPremiumOrder(OrderFindNamesModel findModel);

    OrderModel getPremiumModelByName(String orderType, Double priOrder);
}
