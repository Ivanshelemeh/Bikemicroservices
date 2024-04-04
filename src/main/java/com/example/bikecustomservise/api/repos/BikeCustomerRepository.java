package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * author shele
 *
 */
public interface BikeCustomerRepository extends JpaRepository<BikeCustomer, Integer> {

    @EntityGraph(value = "bikecustomer-graph", attributePaths = {"order"},type = EntityGraph.EntityGraphType.LOAD)
    BikeCustomer findBikeCustomerById(Integer id);

    List<BikeCustomer> findAll();

    @Modifying
    @Query("delete from BikeCustomer bk where bk.email=:email and bk.email is not null")
    void deleteByEmail(@Param("email") String email);

    @Modifying
    @Query("delete from BikeCustomer bk where bk.id = :bId")
    void deleteBikeCustomerById(@Param("bId") Integer id);

    @EntityGraph(value = "bikecustomer-graph",attributePaths = {"order"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select bk from BikeCustomer bk where bk.password =: password group by bk.password")
    BikeCustomer findByPassword(@Param("password") String password);

}
