package com.example.bikecustomservise.api.service.customer.premium;

import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerPremiumFind;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface BikeCustomerPremiumService {

    Set<BikeCustomerModel> findPremium(@NotNull BikeCustomerPremiumFind premiumFind);
}
