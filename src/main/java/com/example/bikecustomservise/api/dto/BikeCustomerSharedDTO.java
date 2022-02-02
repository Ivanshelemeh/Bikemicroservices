package com.example.bikecustomservise.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Getter
@Setter
public class BikeCustomerSharedDTO implements Serializable {
    @NotNull
    private String nameCustomer;
    @NotNull
    private String password;
    private Integer id;
}
