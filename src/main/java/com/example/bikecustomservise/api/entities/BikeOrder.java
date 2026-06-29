package com.example.bikecustomservise.api.entities;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bike_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "order-graph", attributeNodes = {@NamedAttributeNode(value = "customers")})
public class BikeOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "product_name", unique = true)
    @NotNull
    private String nameOrder;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "id")
    @BatchSize(size = 100)
    @EqualsAndHashCode.Exclude
    private List<BikeCustomer> customers;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE
    }, mappedBy = "bikeOrder")
    @EqualsAndHashCode.Exclude
    private Set<BikeOrderItems> orderItems;

    @Version
    private int version;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "lastmod_at")
    private Instant lastModified;


}
