package com.example.bikecustomservise.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "bike_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
@NamedEntityGraph(name = "order-graph", attributeNodes = {@NamedAttributeNode(value = "customers")})
public class BikeOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", unique = true)
    @NotNull
    private String nameOrder;

    @Column(name = "price")
    private double priceOrder;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    @BatchSize(size = 100)
    private List<BikeCustomer> customers;

    @Version
    private int version;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "lastmod_at")
    private Instant lastModified;

    @Column(name = "premium_order")
    @Enumerated(value = EnumType.STRING)
    private PremiumOrder premiumOrder;

    @Column(name = "order_type")
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

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
