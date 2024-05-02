package com.example.bikecustomservise.api.dto;

import com.example.bikecustomservise.api.validation.CustomNameValid;
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
    @CustomNameValid
    private String nickName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
