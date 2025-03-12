package com.example.bikecustomservise.api.repos.transaction;

import com.example.bikecustomservise.api.entities.CustomerTransaction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionRepository implements BikeCustomerTransaction {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<com.example.bikecustomservise.api.entities.CustomerTransaction> findTransactions(@Param("cusId")Integer customerId,
                                                                                                 @Param("start") LocalDateTime startTime,
                                                                                                 @Param("end") LocalDateTime endTime) {
        String jpql = "SELECT ct FROM CustomerTransaction ct "
                + "JOIN FETCH ct.customer bk "
                + "WHERE bk.id = :cusId "
                + "AND ct.period BETWEEN :start AND :end";

        TypedQuery<CustomerTransaction> query = entityManager.createQuery(jpql, CustomerTransaction.class)
                .setParameter("cusId", customerId)
                .setParameter("start", startTime)
                .setParameter("end", endTime);

        return query.getResultList();
    }
}
