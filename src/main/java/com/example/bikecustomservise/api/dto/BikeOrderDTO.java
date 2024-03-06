package com.example.bikecustomservise.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class BikeOrderDTO {
    private String nameOrder;

    @NotNull
    @PositiveOrZero
    private Double priceOrder;
}
