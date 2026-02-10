package com.example.bikecustomservise.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeCustomerResponseModel {


    private String nameCustomer;
    private String passwordCustomer;
    private String email;
    private Integer id;
}
