package com.example.bikecustomservise.api.rest;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerUpdateDto;
import com.example.bikecustomservise.api.model.*;
import com.example.bikecustomservise.api.rest.api.BikeCustomerApi;
import com.example.bikecustomservise.api.service.BikeCustomerService;
import com.example.bikecustomservise.api.validation.CustomNameValid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BikeCustomerController implements BikeCustomerApi {

    private final BikeCustomerService service;

    @Override
    public ResponseEntity<PageDtoRs<BikeCustomerModel>> find(int size, int page) {
        final var customPage = service.findAll(
                new BikeCustomerFind(
                        new PageRq(size, page)
                )
        );
        return ResponseEntity.ok(new PageDtoRs<>(
                customPage.content(),
                customPage.pageSize(),
                customPage.hasNext(),
                customPage.pageNumber(),
                customPage.totalElements()

        ));

    }

    @Override
    public ResponseEntity<BikeCustomerModel> getCustomer(final Integer id) {
        return ResponseEntity.ok(service.findCustomer(id));

    }

    @Override
    public ResponseEntity<UpdateResponse> createCustomer(@Validated @RequestBody BikeCustomerDTO dto) {
        if (Objects.isNull(dto)) {
            log.error(" there is no any customer {}", dto);
            return ResponseEntity.noContent().build();
        }
        final var bikeModel = mapFromDto(dto);
        service.save(bikeModel);
        return ResponseEntity.ok(new UpdateResponse(bikeModel.customerEmail()));

    }

    @Override
    public ResponseEntity<UpdateResponse> updateCustomer(@PathVariable("nickName") @CustomNameValid String nickName,
                                                         @RequestBody @Validated BikeCustomerUpdateDto customer) {
        if (nickName.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        final var updateModel = service.update(nickName, mapFromUpdateDto(customer));
        return ResponseEntity.ok(new UpdateResponse(updateModel.customerEmail()));

    }

    @Override
    public ResponseEntity<Void> deleteCustomer(@PathVariable @NonNull Integer id) {
        service.deleteBikeCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @NonNull
    private BikeCustomerModel mapFromDto(final BikeCustomerDTO bikeCustomerDTO) {
        return new BikeCustomerModel(
                bikeCustomerDTO.getNickName(),
                bikeCustomerDTO.getPassword(),
                bikeCustomerDTO.getEmail()
        );

    }

    @NonNull
    private BikeCustomerUpdateModel mapFromUpdateDto(final BikeCustomerUpdateDto updateDto) {
        return new BikeCustomerUpdateModel(
                updateDto.name(),
                updateDto.email()
        );
    }
}
