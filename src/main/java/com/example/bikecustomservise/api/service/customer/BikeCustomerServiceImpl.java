package com.example.bikecustomservise.api.service.customer;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.customer.BikeCustomerFind;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerUpdateModel;
import com.example.bikecustomservise.api.repos.customer.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import com.example.bikecustomservise.api.validation.CustomNameValid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Qualifier("customService")
public class BikeCustomerServiceImpl implements BikeCustomerService {

    private final BikeCustomerRepository bikeCustomerRepository;
    private final BikeCustomerMapper customerMapper;


    @Override
    public PageRs<BikeCustomerModel> findAll(final BikeCustomerFind customerFind) {
        final Page<BikeCustomer> customerPage = bikeCustomerRepository.findAll(
                customerFind.priceOrder(),
                PageRequest.of(
                        customerFind.pageRq().getPage(),
                        customerFind.pageRq().getSize()
                )
        );
        return new PageRs<>(customerPage.getContent()
                .stream()
                .map(this::mapFromEntity)
                .toList(),
                customerPage.getSize(),
                customerPage.hasNext(),
                customerPage.getNumber(),
                Math.toIntExact(customerPage.getTotalElements()));


    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public BikeCustomerModel findCustomer(final Integer id) {
        final var customer = bikeCustomerRepository
                .findBikeCustomerById(id)
                .orElseThrow(() -> new ServiceProccessingException(USER_NOT_FOUND));
        return mapFromEntity(customer);

    }

    @Override
    @CacheEvict(value = "cacheConf", key = "#email")
    public void deleteCustomer(@NonNull @Email String email) {
        log.debug("Deleting customer by email{}", email);
        bikeCustomerRepository.deleteByEmail(email);
        log.info("Success deleting customer by email{}", email);

    }

    @Override
    @SneakyThrows
    @Transactional
    public BikeCustomerModel create(@NonNull final BikeCustomerModel customer) {
        log.debug("saving customer in db{}", customer.customerEmail());
        final var saved = bikeCustomerRepository.save(customerMapper.mapFromModel(customer));
        return mapFromEntity(saved);
    }

    @SneakyThrows
    @Override
    public BikeCustomerModel update(@NonNull @CustomNameValid final String name,
                                    final BikeCustomerUpdateModel updateModel) {
        log.debug("updating customer by his name{}", name);
        final var existingCustomer = bikeCustomerRepository.findBikeCustomerByNickName(name)
                .orElseThrow(() -> new ServiceProccessingException(USER_NOT_FOUND));
        final var updateCustomer = customerMapper.mapFromUpdateModel(updateModel);
        updateCustomer.setId(existingCustomer.getId());
        final var savedCustomer = bikeCustomerRepository.save(updateCustomer);
        return mapFromEntity(savedCustomer);

    }

    private BikeCustomerModel mapFromEntity(@NonNull final BikeCustomer customer) {
        return new BikeCustomerModel(
                customer.getNickName(),
                customer.getEmail(),
                customer.getPassword()
        );

    }

}