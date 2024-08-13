package com.example.bikecustomservise.api.rest.api;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerUpdateDto;
import com.example.bikecustomservise.api.model.BikeCustomerModel;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rest/api/v1/customers")
public interface BikeCustomerApi {

    @GetMapping
    ResponseEntity<PageDtoRs<BikeCustomerModel>> find(@RequestParam int size,
                                                      @RequestParam int page);
    @GetMapping("{/id}")
    ResponseEntity<BikeCustomerModel> getCustomer(@PathVariable Integer id);

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponse> createCustomer(@Validated @RequestBody BikeCustomerDTO dto);

    @PatchMapping(value = "/{nickName}", consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<UpdateResponse> updateCustomer(@PathVariable("nickName") @CustomNameValid String nickName,
                                                  @RequestBody @Validated BikeCustomerUpdateDto updateDto);

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteCustomer(@PathVariable Integer id);
}
