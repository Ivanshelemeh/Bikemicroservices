package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BikeCustomerRepository extends JpaRepository<BikeCustomer, Integer> {
    BikeCustomer findBikeCustomerById(Integer id);

    List<BikeCustomer> findAll();

    void deleteAllByEmail(String email);

    void deleteBikeCustomerById(Integer id);

}
