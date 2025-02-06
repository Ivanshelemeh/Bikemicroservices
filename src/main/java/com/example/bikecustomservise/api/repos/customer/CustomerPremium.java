package com.example.bikecustomservise.api.repos.customer;

import com.example.bikecustomservise.api.entities.BikeCustomer;

import java.util.List;

public interface CustomerPremium {

    List<BikeCustomer> findPremiumCustomer(int pageSize, int page);
}
