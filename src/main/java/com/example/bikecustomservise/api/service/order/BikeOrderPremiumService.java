package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.model.order.OrderFindNamesModel;
import com.example.bikecustomservise.api.model.order.OrderModel;

import java.util.List;

public interface BikeOrderPremiumService {

    List<OrderModel> findPremiumOrder(OrderFindNamesModel findModel);
}
