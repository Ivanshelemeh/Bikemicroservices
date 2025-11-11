package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import com.example.bikecustomservise.api.model.PageRq;
import com.example.bikecustomservise.api.model.order.OrderFindNamesModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.service.order.BikeOrderPremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BikeOrderPremiumController implements BikeOrderPremiumApi {

    private final BikeOrderPremiumService orderPremiumService;

    @Override
    public ResponseEntity<List<BikeOrderDTO>> getPremiumOrders(int pageOrder, int sizeOrder) {
        return ResponseEntity.ok(orderPremiumService.findPremiumOrder(new OrderFindNamesModel(
                new PageRq(
                        sizeOrder,
                        pageOrder
                )))
                .stream()
                .map(this::mapFromModel)
                .toList());
    }

    private BikeOrderDTO mapFromModel(final OrderModel orderModel) {
        return new BikeOrderDTO(orderModel.orderName(),
                orderModel.itemList());
    }
}
