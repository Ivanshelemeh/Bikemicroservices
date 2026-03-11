package com.example.bikecustomservise.api.service.order.service;

import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.order.OrderCreateModel;
import com.example.bikecustomservise.api.model.order.OrderFindPricesModel;
import com.example.bikecustomservise.api.model.order.OrderModel;

public interface BikeOrderService {


    PageRs<OrderModel> find(final OrderFindPricesModel findModel);

    OrderModel findByOrderId(final Integer id);

    void deleteByOrderName(final String name);

    OrderModel saveOrder(final OrderCreateModel createModel) throws ServiceProccessingException;

}
