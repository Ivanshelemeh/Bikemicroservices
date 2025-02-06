package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.model.order.OrderTypedFindModel;
import com.example.bikecustomservise.api.model.order.OrderWithTypeModel;

import java.util.List;

public interface BikeOrderWithTypeService {

    List<OrderWithTypeModel> findTypedOrder(OrderTypedFindModel findModel);
}
