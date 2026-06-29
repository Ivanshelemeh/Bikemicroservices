package com.example.bikecustomservise.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bike_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class BikeOrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "order_price")
    private BigDecimal price;

    @Column(name = "premium_order")
    @Enumerated(value = EnumType.STRING)
    private PremiumOrder premiumOrder;

    @Column(name = "order_type")
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    @Embedded
    private OrderDetails orderDetails;

    @ManyToOne(targetEntity = BikeCustomer.class, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE
    })
    @JoinColumn(name = "bike_order_id", insertable = false, updatable = false)
    @BatchSize(size = 10)
    private BikeOrder bikeOrder;

    public enum PremiumOrder {
        TRUE,
        FALSE
    }

    public enum OrderType {
        BIKE,
        ENGINE,
        DETAIL
    }


}
