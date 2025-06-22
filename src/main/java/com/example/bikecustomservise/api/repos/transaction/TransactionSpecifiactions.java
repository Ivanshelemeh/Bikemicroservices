package com.example.bikecustomservise.api.repos.transaction;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.entities.CustomerTransaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDateTime;

public class TransactionSpecifiactions {

    public static Specification<CustomerTransaction> hasPeriodTransactionsInRange(LocalDateTime startDate,
                                                                                  LocalDateTime endDate) {
        return ((root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null)
                return criteriaBuilder.conjunction();
            Join<CustomerTransaction, BikeCustomer> customerJoin = root.join("customer", JoinType.INNER);

            return criteriaBuilder.between(customerJoin.get("period"), startDate, endDate);
        });

    }

    public static Specification<CustomerTransaction> hasTransactionsCategory(@NonNull String statusTransaction) {
        return (root, query, cb) -> {
            if (StringUtils.isEmpty(statusTransaction)) {
                return cb.conjunction();
            }

            return cb.equal(root.get("status"), statusTransaction);
        };

    }
}
