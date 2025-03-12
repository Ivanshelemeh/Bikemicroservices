package com.example.bikecustomservise.api.entities;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cus_transaction")
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@DynamicInsert
@DynamicUpdate
@NamedEntityGraph(name = "transaction-graph", attributeNodes = {@NamedAttributeNode(value = "customer")})
@Getter
@Setter
public class CustomerTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "period_transaction")
    private LocalDateTime period;

    @ManyToOne(targetEntity = BikeCustomer.class, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE
    })
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    @BatchSize(size = 100)
    private BikeCustomer customer;

    @Column(name = "transaction_status")
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;

    public enum TransactionStatus {
        FAIL,
        PASS
    }
}
