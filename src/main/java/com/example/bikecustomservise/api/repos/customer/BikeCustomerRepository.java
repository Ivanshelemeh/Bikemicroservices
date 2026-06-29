package com.example.bikecustomservise.api.repos.customer;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * author shele
 */

public interface BikeCustomerRepository extends JpaRepository<BikeCustomer, Integer> {

    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeCustomer> findBikeCustomerById(Integer id);

    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<BikeCustomer> findBikeCustomerByNickName(String name);


    @Query("SELECT  bc from BikeCustomer bc WHERE (: cursor IS NULL OR bc.id > :cursor) ORDER BY  bc.id")
    List<BikeCustomer> fetchPagesCustomers(@Param("cursor") Long cursor, Pageable pageable);

    @Modifying
    @Query("delete from BikeCustomer bk where bk.email= :email and bk.email is not null")
    void deleteByEmail(@Param("email") String email);
    
    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select bk from BikeCustomer bk where bk.password = :password group by bk.password")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")})
    BikeCustomer findByPassword(@Param("password") String password);

}
