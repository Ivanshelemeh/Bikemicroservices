package com.example.bikecustomservise.api.rest.api.customer;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerUpdateDto;
import com.example.bikecustomservise.api.model.PageDtoRs;
import com.example.bikecustomservise.api.model.UpdateResponse;
import com.example.bikecustomservise.api.model.customer.BikeCustomerFindModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.validation.CustomNameValid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rest/api/v1/customers")
public interface BikeCustomerApi {

    @GetMapping
    ResponseEntity<PageDtoRs<BikeCustomerModel>> find(@RequestParam int size,
                                                      @RequestParam int page,
                                                      @RequestParam double price);

    @GetMapping("/{id}")
    ResponseEntity<BikeCustomerFindModel> get(@PathVariable Integer id);

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateResponse> create(@Validated @RequestBody BikeCustomerDTO dto);

    @PatchMapping(value = "/{nickName}", consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<UpdateResponse> update(@PathVariable("nickName") @CustomNameValid String nickName,
                                                  @RequestBody @Validated BikeCustomerUpdateDto updateDto);

    @DeleteMapping(value = "/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> delete(@PathVariable String email);
}
