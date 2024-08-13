package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Integer> {
    @EntityGraph(value = "order-graph", attributePaths = {"customers"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeOrder> findBikeOrderById(Integer id);

    void deleteBikeOrderById(Integer id);

    @Query("select bo from BikeOrder bo where bo.priceOrder > 0.0 order by bo.priceOrder limit 100")
    @QueryHints(value = {@QueryHint(name = "javax.persistent.query.timeout", value = "3000")})
    List<BikeOrder> findAll();
}
