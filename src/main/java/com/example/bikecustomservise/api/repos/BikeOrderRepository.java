package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeOrderRepository extends JpaRepository<BikeOrder,Integer> {
}
