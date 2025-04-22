package com.example.bikecustomservise.api.entities;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(value = "SELECT bk.id as bike_customer_id," +
        "count(DISTINCT ct.id) as customer_transactions," +
        "max(bo.price) as total_order_price" +
        "FROM BikeCustomer bk " +
        "JOIN CustomerTransaction ct ON ct.customer_id = bk.id" +
        "JOIN BikeOrder bo ON bo.id = bk.id " +
        "GROUP BY bk.id HAVING total_order_price > 10000")
@Immutable
@Synchronize(value = {"BikeCustomer,CustomerTransaction,BikeOrder"})
public record TransactionSummary(
        @Id
        Integer bikeCustomerId,
        Double totalOrderPrice,
        Long totalTransactions

) {

}
