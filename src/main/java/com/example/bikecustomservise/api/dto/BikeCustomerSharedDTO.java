package com.example.bikecustomservise.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;



@Getter
@Setter
public class BikeCustomerSharedDTO implements Serializable {

    @NotNull
    private String nameCustomer;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private Integer id;
}
