package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.model.order.OrderTypedFindModel;
import com.example.bikecustomservise.api.model.order.OrderWithTypeModel;

public interface BikeOrderWithTypeService {

    OrderWithTypeModel findTypedOrder(OrderTypedFindModel findModel);
}
