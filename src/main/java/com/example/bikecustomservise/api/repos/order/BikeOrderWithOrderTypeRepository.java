package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BikeOrderWithOrderTypeRepository implements BikeOrderWithOrderType {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BikeOrder> findBikeOrdersWithCurrentType(double priceOrder, String orderType, int pageOrder, int sizeOrder) {
        TypedQuery<BikeOrder> typedQuery = entityManager.createQuery(
                "SELECT bo FROM BikeOrder bo where bo.priceOrder = :priceOrder AND bo.orderType = :orderType " +
                        "ORDER BY bo.priceOrder", BikeOrder.class);
        typedQuery.setParameter("priceOrder", priceOrder);
        typedQuery.setParameter("orderType", orderType);
        typedQuery.setMaxResults(sizeOrder);
        return typedQuery.getResultList();
    }
}
