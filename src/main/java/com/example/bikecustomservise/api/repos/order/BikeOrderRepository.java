package com.example.bikecustomservise.api.repos.order;

import com.blazebit.persistence.CriteriaBuilder;
import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.NonNull;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Integer> , KeysetAwareRepository<BikeOrder, Long> {
    @EntityGraph(value = "order-graph", attributePaths = {"customers"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeOrder> findBikeOrderById(@NonNull Integer id);

    @Query("SELECT bo FROM BikeOrder bo  WHERE bo IN :orderNames ORDER BY bo.createdAt ASC ")
    @QueryHints(value = {@QueryHint(name = "javax.persistent.query.timeout", value = "3000")})
    KeysetPaget<BikeOrder> findOrders(@NonNull CriteriaBuilder builder, Pageable pageable);

    @Query(value = "SELECT bo from BikeOrder bo JOIN FETCH BikeCustomer bk ON bk.id = bo.id " +
            "JOIN FETCH BikeOrderItems bi ON bo.id = bi.id WHERE bi.premiumOrder IS FALSE")
    Page<BikeOrder> findPremiumOrder(Pageable pageable);

    @Query("SELECT bo from BikeOrder bo JOIN FETCH BikeCustomer bk ON bk.id = bo.id " +
            "JOIN FETCH BikeOrderItems bi ON bo.id = bi.id WHERE bi.orderType =: currentType AND bi.price =: orderPrice ")
    Optional<BikeOrder> findCurrentOrderType( String currentType, Double orderPrice);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM BikeOrder bo WHERE bo.nameOrder = :orderName")
    void deleteBikeOrder(@NonNull  String orderName);

    boolean existsBikeOrderByNameOrder(@NonNull String orderName);
}
