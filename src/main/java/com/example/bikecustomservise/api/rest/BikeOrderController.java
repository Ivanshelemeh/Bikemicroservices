package com.example.bikecustomservise.api.rest;

import com.example.bikecustomservise.api.dto.BikeOrderCreateDTO;
import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.PageRq;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.model.order.OrderCreateModel;
import com.example.bikecustomservise.api.model.order.OrderFindModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.rest.api.BikeOrderApi;
import com.example.bikecustomservise.api.service.BikeOrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BikeOrderController implements BikeOrderApi {

    private final BikeOrderService bikeOrderService;

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
}
