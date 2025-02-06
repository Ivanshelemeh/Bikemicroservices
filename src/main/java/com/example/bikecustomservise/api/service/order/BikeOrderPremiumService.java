package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.model.order.OrderFindModel;
import com.example.bikecustomservise.api.model.order.OrderModel;

import java.util.Set;

public interface BikeOrderPremiumService {

    Set<OrderModel> findPremiumOrder(OrderFindModel findModel);
}
