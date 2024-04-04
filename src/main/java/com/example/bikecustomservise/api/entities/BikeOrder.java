package com.example.bikecustomservise.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NamedEntityGraph(name = "order-graph",attributeNodes = {@NamedAttributeNode(value = "customers")})
public class BikeOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", unique = true)
    @NotNull
    private String nameOrder;

    @Column(name = "price")
    private double priceOrder;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "id")
   // @JoinColumn(name = "id", insertable = false, updatable = true, referencedColumnName = "id", nullable = true)
    private List<BikeCustomer> customers;

    @Version
    private int version;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "lastmod_at")
    private Instant lastModified;
}
