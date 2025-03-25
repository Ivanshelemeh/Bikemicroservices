package com.example.bikecustomservise.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetails implements Serializable {

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "order_info")
    private String orderInfo;
}
