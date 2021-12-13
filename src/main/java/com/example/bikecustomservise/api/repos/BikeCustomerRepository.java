package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeCustomerRepository extends JpaRepository<BikeCustomer,Integer> {
}
