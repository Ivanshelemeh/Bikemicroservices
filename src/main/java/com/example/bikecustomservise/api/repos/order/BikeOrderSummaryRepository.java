package com.example.bikecustomservise.api.repos.order;

import com.example.bikecustomservise.api.entities.BikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeOrderSummaryRepository  extends JpaRepository<BikeOrder, Integer> {

    Optional<BikeOrderNameSummary> findBikeOrdersByNameOrder(String orderName);
}
