package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/rest/api/v1/orders/premium")
public interface BikeOrderPremiumApi {

    @GetMapping("/orders")
    ResponseEntity<List<BikeOrderDTO>> getPremiumOrders(@RequestParam int pageOrder,
                                                        @RequestParam int sizeOrder);
}
