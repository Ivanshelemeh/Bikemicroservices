package com.example.bikecustomservise.api.repos.premium;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BikeCustomerPremiumRepository extends JpaRepository<BikeCustomer, Integer> {

    @Query("SELECT bk from BikeCustomer bk join fetch BikeOrder bo on bo.id = bk.id WHERE bk.premiumCustomer is not null " +
            "ORDER BY bk.id")
    Page<BikeCustomer> findBikeCustomerPremium(Pageable pageable);
}
