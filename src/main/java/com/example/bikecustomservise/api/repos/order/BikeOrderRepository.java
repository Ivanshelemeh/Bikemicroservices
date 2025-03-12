package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.NonNull;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Integer> {
    @EntityGraph(value = "order-graph", attributePaths = {"customers"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeOrder> findBikeOrderById(@NonNull final Integer id);

    @Query("SELECT bo FROM BikeOrder bo WHERE bo.priceOrder = :price  ORDER BY bo.priceOrder DESC ")
    @QueryHints(value = {@QueryHint(name = "javax.persistent.query.timeout", value = "3000")})
    Page<BikeOrder> findOrders(@NonNull final Double price, Pageable pageable);


    @Modifying
    @Query("DELETE FROM BikeOrder bo WHERE bo.nameOrder = :orderName")
    void deleteBikeOrder(@NonNull final String orderName);

    boolean existsBikeOrderByNameOrder(@NonNull String orderName);
}
