package com.example.bikecustomservise.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@Getter
@Setter
public class BikeCustomerResponseModel {

    private String nameCustomer;
    private String passwordCustomer;
    private String email;
    private Integer id;
}
