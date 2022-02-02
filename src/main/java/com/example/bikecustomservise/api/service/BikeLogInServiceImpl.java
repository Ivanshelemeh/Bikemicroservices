package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BikeLogInServiceImpl implements BikeLogInService{

    private final BikeCustomerRepository repository;
    private BikeCustomerMapper mapper;

    @Autowired
    public BikeLogInServiceImpl(BikeCustomerRepository repository, BikeCustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BikeCustomerSharedDTO create(BikeCustomerSharedDTO dto) {
        BikeCustomer customer = mapper.mapToCustomerEntity(dto);
        repository.save(customer);
        BikeCustomerSharedDTO bikeCustomerSharedDTO = mapper.mapToDToShared(customer);
        return bikeCustomerSharedDTO;
    }
}
