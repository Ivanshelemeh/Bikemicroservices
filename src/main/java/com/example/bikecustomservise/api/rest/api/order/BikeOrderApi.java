package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderCreateDTO;
import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import com.example.bikecustomservise.api.dto.order.BikeOrderWithTypeDTO;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.model.order.OrderModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/rest/api/v1/orders")
public interface BikeOrderApi {

    @GetMapping
    ResponseEntity<PageDtoRs<OrderModel>> find(@RequestParam(value = "costs")List<Double> costs,
                                               @RequestParam int size,
                                               @RequestParam int page);

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BikeOrderDTO> getOrder(@PathVariable Integer id);

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponse> createOrder(@RequestBody @Valid BikeOrderCreateDTO createDTO);

    @DeleteMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteOrder(@PathVariable String name);

    ResponseEntity<BikeOrderWithTypeDTO> getOrdersWithType(@RequestParam String type,
                                                                 @RequestParam double priceOrder);


}
