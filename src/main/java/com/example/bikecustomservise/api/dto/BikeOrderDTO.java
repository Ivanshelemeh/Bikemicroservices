package com.example.bikecustomservise.api.dto;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BikeOrderDTO {
    private String nameOrder;
    private List<BikeCustomer> customerList;
    private Double priceOrder;
    private Integer id;
}
