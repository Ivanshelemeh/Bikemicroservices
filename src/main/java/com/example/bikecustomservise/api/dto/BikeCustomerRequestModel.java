package com.example.bikecustomservise.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class BikeCustomerRequestModel {

    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private Integer id;
}
