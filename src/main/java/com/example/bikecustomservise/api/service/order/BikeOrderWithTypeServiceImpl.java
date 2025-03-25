package com.example.bikecustomservise.api.service.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.order.OrderTypedFindModel;
import com.example.bikecustomservise.api.model.order.OrderWithTypeModel;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.ORDER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BikeOrderWithTypeServiceImpl implements BikeOrderWithTypeService {

    private final BikeOrderRepository repository;


    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public OrderWithTypeModel findTypedOrder(@NotNull final OrderTypedFindModel findModel) {
        final var orEntity = repository.findCurrentOrderType(
                        findModel.orderType(),
                        findModel.priceOrderType())
                .orElseThrow(() -> new ServiceProccessingException(ORDER_NOT_FOUND));
        return mapFromEntity(orEntity);

    }


    private OrderWithTypeModel mapFromEntity(final BikeOrder bikeOrder) {
        return new OrderWithTypeModel(
                bikeOrder.getNameOrder(),
                bikeOrder.getPriceOrder(),
                bikeOrder.getOrderType().name()
        );
    }
}
