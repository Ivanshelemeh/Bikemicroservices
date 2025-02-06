package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/rest/api/v1/orders/premium")
public interface BikeOrderPremiumApi {

    ResponseEntity<List<BikeOrderDTO>> getPremiumOrders(@RequestParam(defaultValue = "0") int pageOrder,
                                                        @RequestParam(defaultValue = "0") int sizeOrder,
                                                        @RequestParam @Positive double priceOrder);
}
