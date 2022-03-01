package com.example.bikecustomservise.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class BikeCustomerSingInModel {

    @NotNull
    private String email;
    @NotNull
    private String password;
    private Integer id;
}
