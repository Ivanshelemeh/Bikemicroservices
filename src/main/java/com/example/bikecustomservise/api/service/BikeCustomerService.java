package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.model.BikeCustomerFind;
import com.example.bikecustomservise.api.model.BikeCustomerModel;
import com.example.bikecustomservise.api.model.BikeCustomerUpdateModel;
import com.example.bikecustomservise.api.model.PageRs;

public interface BikeCustomerService {

    PageRs<BikeCustomerModel> findAll(final BikeCustomerFind customerFind);

    BikeCustomerModel findCustomer(Integer id);

    void deleteBikeCustomerById(Integer id);

    void save(BikeCustomerModel customer);

    BikeCustomerModel update(final String name, final BikeCustomerUpdateModel updateModel);

}