package com.example.bikecustomservise.api.service.customer.premium;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerPremiumFind;
import com.example.bikecustomservise.api.repos.premium.BikeCustomerPremiumRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeCustomerPremiumImpl implements BikeCustomerPremiumService {

    private final BikeCustomerPremiumRepository premiumRepository;

    @SneakyThrows
    @Override
    public Set<BikeCustomerModel> findPremium(@NonNull final BikeCustomerPremiumFind premiumFind) {
        return premiumRepository.findBikeCustomerPremium(PageRequest.of(
                        premiumFind.request().getSize(),
                        premiumFind.request().getPage()
                ))
                .stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toSet());

    }

    private BikeCustomerModel mapFromEntity(@NonNull final BikeCustomer customer) {
        return new BikeCustomerModel(
                customer.getNickName(),
                customer.getEmail(),
                customer.getPassword()
        );

    }
}
