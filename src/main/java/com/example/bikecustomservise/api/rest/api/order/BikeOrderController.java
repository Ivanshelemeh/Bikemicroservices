package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderCreateDTO;
import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import com.example.bikecustomservise.api.dto.order.BikeOrderWithTypeDTO;
import com.example.bikecustomservise.api.dto.order.OrderItemDTO;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.PageRq;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.model.order.*;
import com.example.bikecustomservise.api.service.order.service.BikeOrderService;
import com.example.bikecustomservise.api.service.order.service.BikeOrderWithTypeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BikeOrderController implements BikeOrderApi, BikeOrderV2Api {

    private final BikeOrderService bikeOrderService;
    private final BikeOrderWithTypeService typeService;

    @Override
    public ResponseEntity<PageDtoRs<OrderModel>> find(List<String>  orderNames, int size, int page) {
        final var orderPage = bikeOrderService.find(
                new OrderFindPricesModel(orderNames,
                        new PageRq(size, page))
        );
        return ResponseEntity.ok(new PageDtoRs<>(
                orderPage.content(),
                orderPage.pageSize(),
                orderPage.hasNext(),
                orderPage.pageNumber()
        ));
    }

    @Override
    public ResponseEntity<BikeOrderWithTypeDTO> getOrdersWithType(String type, double priceOrder) {
        final var orderType = typeService.findTypedOrder(new OrderTypedFindModel(
                type,
                priceOrder
        ));
        return ResponseEntity.ok(mapFromOrderTypeModel(orderType));
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
        return ResponseEntity.ok(new UpdateResponse(order.orderName()));
    }

    @Override
    public ResponseEntity<Void> deleteOrder(String name) {
        bikeOrderService.deleteByOrderName(name);
        return ResponseEntity.noContent().build();
    }

    private BikeOrderDTO mapFromModel(final OrderModel orderModel) {
        return new BikeOrderDTO(orderModel.orderName(),
                orderModel.itemList()
                        .stream()
                        .map(this::mapFromOrderItemModel)
                        .toList());
    }

    private OrderCreateModel mapFromDTO(final BikeOrderCreateDTO orderCreateDTO) {
        return new OrderCreateModel(
                orderCreateDTO.orderName(),
                orderCreateDTO.email(),
                orderCreateDTO.orderCost()
        );
    }

    private OrderItemDTO mapFromOrderItemModel(OrderItem item) {
        return new OrderItemDTO(
                item.orderPrice(),
                item.orderPremium(),
                item.orderType()
        );

    }

    private BikeOrderWithTypeDTO mapFromOrderTypeModel(final OrderWithTypeModel model) {
        return new BikeOrderWithTypeDTO(
                model.orderName(),
                model.orderCost(),
                model.orderType()
        );
    }

 /*   private OrderRangDTO mapFromOrderRecommendationModel(OrderRecommendationProcessModel model) {
        return new OrderRangDTO(
                model.processedDescription(),
                model.orderRang()
        );
    }*/

    @Override
    public ResponseEntity<BikeOrderDTO> getOrder(Integer id, Boolean isRating) {
        return null;
    }
}
