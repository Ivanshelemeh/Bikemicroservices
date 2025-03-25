package com.example.bikecustomservise.api.rest.api.customer;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/rest/api/v1/customers/premium")
public interface BikeCustomerPremiumApi {

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<BikeCustomerDTO>> getPremiumCustomers(@RequestParam int pageNumber,
                                                              @RequestParam int sizeNumber);
}
