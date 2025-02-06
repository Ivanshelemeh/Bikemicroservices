package com.example.bikecustomservise.api.rest.api.customer;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.model.PageRq;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerPremiumFind;
import com.example.bikecustomservise.api.service.customer.premium.BikeCustomerPremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BikeCustomerPremiumController implements BikeCustomerPremiumApi {

    private final BikeCustomerPremiumService premiumService;

    @Override
    public ResponseEntity<List<BikeCustomerDTO>> getPremiumCustomers(int pageNumber, int sizeNumber) {
        final var premiumCustomers = premiumService.findPremium(new BikeCustomerPremiumFind(
                        new PageRq(sizeNumber, pageNumber)
                )).stream()
                .map(this::mapFromModel)
                .toList();
        return ResponseEntity.ok(premiumCustomers);
    }

    private BikeCustomerDTO mapFromModel(@NonNull final BikeCustomerModel model) {
        final var customerDto = new BikeCustomerDTO();
        customerDto.setNickName(model.nameCustomer());
        customerDto.setEmail(model.customerEmail());
        customerDto.setPassword(model.customerPassword());
        return customerDto;
    }
}
