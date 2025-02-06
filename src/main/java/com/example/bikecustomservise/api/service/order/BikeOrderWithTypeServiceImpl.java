package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.model.order.OrderTypedFindModel;
import com.example.bikecustomservise.api.model.order.OrderWithTypeModel;
import com.example.bikecustomservise.api.repos.order.BikeOrderWithOrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeOrderWithTypeServiceImpl implements BikeOrderWithTypeService {

    private final BikeOrderWithOrderType orderWithOrderType;

    @Override
    public List<OrderWithTypeModel> findTypedOrder(@NotNull final OrderTypedFindModel findModel) {
        return orderWithOrderType.findBikeOrdersWithCurrentType(
                        findModel.priceOrderType(),
                        findModel.orderType(),
                        findModel.req().getPage(),
                        findModel.req().getSize()
                ).stream()
                .map(this::mapFromEntity)
                .toList();

    }


    private OrderWithTypeModel mapFromEntity(final BikeOrder bikeOrder) {
        return new OrderWithTypeModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getPriceOrder(),
                bikeOrder.getOrderType().name()
        );
    }
}
