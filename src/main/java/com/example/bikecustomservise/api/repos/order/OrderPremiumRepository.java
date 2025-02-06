package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderPremiumRepository implements OrderPremium {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<BikeOrder> findPremiumOrder(double priceOrder,int pageSize, int page) {
        TypedQuery<BikeOrder> query = manager.createQuery(
                "SELECT bo FROM BikeOrder bo where bo.premiumOrder is true AND bo.priceOrder = :priceOrder ORDER BY bo.createdAt DESC",
                BikeOrder.class);
        query.setParameter("priceOrder",priceOrder);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
