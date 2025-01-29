package com.example.bikecustomservise.api.rest.api;

import com.example.bikecustomservise.api.dto.BikeOrderCreateDTO;
import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.model.order.OrderModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/rest/api/v1/orders")
public interface BikeOrderApi {

    @GetMapping
    ResponseEntity<PageDtoRs<OrderModel>> find(@RequestParam double priceOrder,
                                               @RequestParam int size,
                                               @RequestParam int page);

    @GetMapping(value = "{/id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BikeOrderDTO> getOrder(@PathVariable Integer id);

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponse> createOrder(@RequestBody @Valid BikeOrderCreateDTO createDTO);

    @DeleteMapping(value = "{/name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteOrder(@PathVariable String name);


}
