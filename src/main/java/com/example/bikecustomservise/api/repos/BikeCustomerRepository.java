package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeCustomer;
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

    BikeCustomer findBikeCustomerById(Integer id);

    List<BikeCustomer> findAll();

    void deleteAllByEmail(String email);

    @Modifying
    @Query("delete from BikeCustomer bk where bk.id = :bId")
    void deleteBikeCustomerById(@Param("bId") Integer id);

    BikeCustomer findByPassword(String password);

}
