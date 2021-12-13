package com.example.bikecustomservise.api.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bike_order")
@AllArgsConstructor
@NoArgsConstructor
public class BikeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", unique = true)
    private String nameOrder;

    @Column(name = "price")
    private double priceOrder;

    @OneToMany(targetEntity = BikeCustomer.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false , updatable = true, referencedColumnName = "id", nullable = false)
    @Column(nullable = true,name = "bike_order_list")
    private List<BikeCustomer> bikeCustomerList;
}
