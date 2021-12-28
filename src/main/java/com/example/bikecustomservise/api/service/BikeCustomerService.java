package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.entities.BikeCustomer;

import java.util.List;
import java.util.Optional;

public interface BikeCustomerService {
    List<BikeCustomer> findAll();

    BikeCustomer findOne(Integer id);

    void deleteBikeCustomerById(Integer id);

    BikeCustomer save(BikeCustomer customer);


}