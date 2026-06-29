package com.example.bikecustomservise.api.service.customer;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.customer.BikeCustomerFind;
import com.example.bikecustomservise.api.model.customer.BikeCustomerFindModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerUpdateModel;
import com.example.bikecustomservise.api.repos.customer.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import com.example.bikecustomservise.api.validation.CustomNameValid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Qualifier("customService")
public class BikeCustomerServiceImpl implements BikeCustomerService {

    private final BikeCustomerRepository bikeCustomerRepository;
    private final BikeCustomerMapper customerMapper;


    @Override
    @Transactional(readOnly = true)
    public PageRs<BikeCustomerModel> findAll(final BikeCustomerFind customerFind) {
        Pageable pageable = PageRequest.of(customerFind.pageRq().getSize(), customerFind.pageRq().getPage());
        var results = bikeCustomerRepository.fetchPagesCustomers(Long.valueOf(customerFind.pageRq().getSize()), pageable);
        boolean hasNext = results.size() == customerFind.pageRq().getSize();
        var nextCursor = hasNext ? results.getLast().getId() : null;
        return new PageRs<>(results
                .stream()
                .map(this::mapFromEntity)
                .toList(),
                results.size(),
                hasNext,
                nextCursor);


    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public BikeCustomerFindModel findCustomer(final Integer id) {
        final var customer = bikeCustomerRepository
                .findBikeCustomerById(id)
                .orElseThrow(() -> new ServiceProccessingException(USER_NOT_FOUND));
        return mapFindModelFromEntity(customer);

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

    private BikeCustomerFindModel mapFindModelFromEntity(@NonNull final BikeCustomer c) {
        return new BikeCustomerFindModel(
                c.getId(),
                c.getNickName()
        );
    }

}