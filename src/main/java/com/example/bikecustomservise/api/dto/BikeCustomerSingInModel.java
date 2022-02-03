package com.example.bikecustomservise.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BikeCustomerSingInModel {

    private String email;
    private String password;
    private Integer id;
}
