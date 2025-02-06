package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.order.OrderCreateModel;
import com.example.bikecustomservise.api.model.order.OrderFindModel;
import com.example.bikecustomservise.api.model.order.OrderModel;

public interface BikeOrderService {


    PageRs<OrderModel> find(final OrderFindModel findModel);

    OrderModel findByOrderId(final Integer id);

    void deleteByOrderName(final String name);

    BikeOrder saveOrder(final OrderCreateModel createModel) throws ServiceProccessingException;
}
