package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.model.BikeCustomerFind;
import com.example.bikecustomservise.api.model.BikeCustomerModel;
import com.example.bikecustomservise.api.model.BikeCustomerUpdateModel;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class BikeCustomerServiceImpl implements BikeCustomerService {

    private final BikeCustomerRepository bikeCustomerRepository;
    private final BikeCustomerMapper customerMapper;


    @Override
    public PageRs<BikeCustomerModel> findAll(final BikeCustomerFind customerFind) {
        final Page<BikeCustomer> customerPage = bikeCustomerRepository.findAll(
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
    @CacheEvict(value = "cacheConf", key = "#id")
    public void deleteBikeCustomerById(@NonNull final Integer id) {
        Optional<BikeCustomer> optionalBikeCustomer = bikeCustomerRepository.findBikeCustomerById(id);
        if (optionalBikeCustomer.isEmpty()) {
            bikeCustomerRepository.delete(optionalBikeCustomer.get());
        }


    }

    @Override
    @SneakyThrows
    @Transactional
    public void save(@NonNull final BikeCustomerModel customer) {
        bikeCustomerRepository.save(customerMapper.mapFromModel(customer));
    }

    @SneakyThrows
    @Override
    public BikeCustomerModel update(final String name,
                                    final BikeCustomerUpdateModel updateModel) {
        if (StringUtils.isEmpty(name) || Objects.isNull(updateModel)) {
            throw new ServiceProccessingException(USER_NOT_FOUND);
        }
        final var bikeCustomer = bikeCustomerRepository
                .save(customerMapper.mapFromUpdateModel(updateModel));
        return mapFromEntity(bikeCustomer);

    }

    private BikeCustomerModel mapFromEntity(@NonNull final BikeCustomer customer) {
        return new BikeCustomerModel(
                customer.getNickName(),
                customer.getEmail(),
                customer.getPassword()
        );

    }

}