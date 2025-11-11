package com.example.bikecustomservise.api.service.customer;

import com.example.bikecustomservise.api.model.customer.BikeCustomerFind;
import com.example.bikecustomservise.api.model.customer.BikeCustomerFindModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerUpdateModel;
import com.example.bikecustomservise.api.model.PageRs;

public interface BikeCustomerService {

    PageRs<BikeCustomerModel> findAll(final BikeCustomerFind customerFind);

    BikeCustomerFindModel findCustomer(Integer id);

    void deleteCustomer(final String customerEmail);

    BikeCustomerModel create(BikeCustomerModel customer);

    BikeCustomerModel update(final String name, final BikeCustomerUpdateModel updateModel);

}