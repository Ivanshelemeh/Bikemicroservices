package com.example.bikecustomservise.api.repos.customer;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * author shele
 */

public interface BikeCustomerRepository extends JpaRepository<BikeCustomer, Integer> {

    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeCustomer> findBikeCustomerById(Integer id);

    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeCustomer> findBikeCustomerByNickName(String name);

    @Query("select bk from BikeCustomer bk  join fetch  " +
            " BikeOrder bo on  bo.id = bk.id where bo.priceOrder = :price and bo.priceOrder = :price")
    Page<BikeCustomer> findAll(@NonNull final Double price, Pageable pageable);

    @Modifying
    @Query("delete from BikeCustomer bk where bk.email= :email and bk.email is not null")
    void deleteByEmail(@Param("email") String email);
    
    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select bk from BikeCustomer bk where bk.password = :password group by bk.password")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")})
    BikeCustomer findByPassword(@Param("password") String password);

}
