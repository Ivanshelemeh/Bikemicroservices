package com.example.bikecustomservise.api.repos.transaction;

import com.example.bikecustomservise.api.entities.CustomerTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {

    @Query("SELECT ct from CustomerTransaction ct JOIN FETCH BikeCustomer bk WHERE bk.id = :customerId")
    List<CustomerTransaction> findAllCustomerTransaction(Integer customerId);

    Page<CustomerTransaction> findAll(Specification specification, Pageable pageable);
}
