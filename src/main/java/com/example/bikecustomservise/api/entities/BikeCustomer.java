package com.example.bikecustomservise.api.entities;

import com.example.bikecustomservise.api.validation.CustomNameValid;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "bike_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "bikecustomer-graph", attributeNodes = {@NamedAttributeNode(value = "order")})
public class BikeCustomer implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true, name = "nickname")
    @CustomNameValid
    private String nickName;

    @Column(name = "mail", nullable = false, unique = true)
    @Size(max = 20)
    @Email
    private String email;

    @Column(name = "password")
    private String password;


    @ManyToOne(targetEntity = BikeOrder.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @BatchSize(size = 100)
    private BikeOrder order;

    @Version
    private int versionId;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "lastmodify_at")
    private Instant lastModified;

    @Column(name = "premium_customer")
    @Enumerated(value = EnumType.STRING)
    private PremiumCustomer premiumCustomer;

    public enum PremiumCustomer {
        TRUE,
        FALSE
    }
}
