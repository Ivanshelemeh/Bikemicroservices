package com.example.bikecustomservise.api.repos;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Integer> {

    BikeOrder findBikeOrderById(Integer id);

    void deleteBikeOrderById(Integer id);

    List<BikeOrder> findAll();
}
