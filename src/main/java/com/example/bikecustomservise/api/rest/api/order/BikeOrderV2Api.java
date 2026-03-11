package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/rest/api/v2/orders")
public interface BikeOrderV2Api {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BikeOrderDTO> getOrder(@PathVariable Integer id, @RequestParam Boolean isRating);
}
