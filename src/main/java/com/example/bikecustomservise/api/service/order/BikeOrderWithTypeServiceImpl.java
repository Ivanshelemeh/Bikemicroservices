package com.example.bikecustomservise.api.service.order;

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
        var orderType = orEntity.getOrderItems()
                .stream()
                .filter(it -> findModel.orderType().equals(it.getOrderType().name()))
                .findFirst()
                .get().getOrderType().name();

        var priceOrder = orEntity.getOrderItems()
                .stream()
                .filter(it -> findModel.priceOrderType() == it.getPrice().doubleValue())
                .findFirst()
                .get().getPrice().doubleValue();

        return new OrderWithTypeModel(
                orEntity.getNameOrder(),
                priceOrder,
                orderType
        );
    }


}
