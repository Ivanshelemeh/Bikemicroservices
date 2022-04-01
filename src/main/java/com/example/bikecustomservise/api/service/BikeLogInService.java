package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BikeLogInService extends UserDetailsService {

    /**
     * Created shared dto
     * @param dto
     * @return
     */
    BikeCustomerSharedDTO create(BikeCustomerSharedDTO dto);

    BikeCustomerSharedDTO getUserDetailsByPassword(String password);
}
