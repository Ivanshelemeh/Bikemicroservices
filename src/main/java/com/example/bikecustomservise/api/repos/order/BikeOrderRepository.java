package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Integer> {
    @EntityGraph(value = "order-graph", attributePaths = {"customers"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeOrder> findBikeOrderById(@NonNull Integer id);

    @Query("SELECT  bo from BikeOrder bo WHERE (: cursor IS NULL OR bo.id > :cursor) ORDER BY  bo.id")
    List<BikeOrder> fetchPagesBikeOrders(@Param("cursor") Long cursor, Pageable pageable);


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
