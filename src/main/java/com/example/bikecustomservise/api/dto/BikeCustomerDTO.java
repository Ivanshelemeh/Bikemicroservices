package com.example.bikecustomservise.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class BikeCustomerDTO {

    @NotNull
    @Size(max = 20)
    private String nickName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
