package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderCreateDTO;
import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import com.example.bikecustomservise.api.dto.order.BikeOrderWithTypeDTO;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.PageRq;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.model.order.*;
import com.example.bikecustomservise.api.service.order.BikeOrderService;
import com.example.bikecustomservise.api.service.order.BikeOrderWithTypeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BikeOrderController implements BikeOrderApi {

    private final BikeOrderService bikeOrderService;
    private final BikeOrderWithTypeService typeService;

    @Override
    public ResponseEntity<PageDtoRs<OrderModel>> find(double priceOrder, int size, int page) {
        final var orderPage = bikeOrderService.find(
                new OrderFindModel(priceOrder,
                        new PageRq(size, page))
        );
        return ResponseEntity.ok(new PageDtoRs<>(
                orderPage.content(),
                orderPage.pageSize(),
                orderPage.hasNext(),
                orderPage.pageNumber(),
                orderPage.totalElements()
        ));
    }

    @Override
    public ResponseEntity<List<BikeOrderWithTypeDTO>> getOrdersWithType(String type, double priceOrder, int sizeOrder, int pageOrder) {
        final var orderTypeList = typeService.findTypedOrder(
                        new OrderTypedFindModel(
                                type,
                                null,
                                priceOrder,
                                new PageRq(
                                        sizeOrder,
                                        pageOrder
                                )
                        )
                ).stream()
                .map(this::mapFromOrderTypeModel)
                .toList();
        return ResponseEntity.ok(orderTypeList);
    }

    @Override
    public ResponseEntity<BikeOrderDTO> getOrder(@NonNull final Integer orderId) {
        final var orderDTO = mapFromModel(bikeOrderService.findByOrderId(orderId));
        return ResponseEntity.ok(orderDTO);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<UpdateResponse> createOrder(@NonNull final BikeOrderCreateDTO createDTO) {
        final var order = bikeOrderService.saveOrder(mapFromDTO(createDTO));
        return ResponseEntity.ok(new UpdateResponse(order.getNameOrder()));
    }

    @Override
    public ResponseEntity<Void> deleteOrder(String name) {
        bikeOrderService.deleteByOrderName(name);
        return ResponseEntity.noContent().build();
    }

    private BikeOrderDTO mapFromModel(final OrderModel orderModel) {
        return new BikeOrderDTO(orderModel.orderName(),
                orderModel.orderPrice());
    }

    private OrderCreateModel mapFromDTO(final BikeOrderCreateDTO orderCreateDTO) {
        return new OrderCreateModel(
                orderCreateDTO.orderName(),
                orderCreateDTO.email(),
                orderCreateDTO.orderCost()
        );
    }

    private BikeOrderWithTypeDTO mapFromOrderTypeModel(final OrderWithTypeModel model) {
        return new BikeOrderWithTypeDTO(
                model.orderName(),
                model.orderCost(),
                model.orderType()
        );
    }
}
