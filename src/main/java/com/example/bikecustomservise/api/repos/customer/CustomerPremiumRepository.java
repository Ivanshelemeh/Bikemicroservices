package com.example.bikecustomservise.api.repos.customer;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerPremiumRepository implements CustomerPremium {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BikeCustomer> findPremiumCustomer(int pageSize, int page) {
        TypedQuery<BikeCustomer> customerQuery = entityManager.createQuery(
                "SELECT bc From BikeCustomer bc where bc.premiumCustomer is true ORDER BY bc.createdAt DESC", BikeCustomer.class);
        customerQuery.setFirstResult(pageSize * page);
        customerQuery.setMaxResults(pageSize);
        return customerQuery.getResultList();
    }
}
