package com.example.bikecustomservise.api.repos.analyze;

import com.example.bikecustomservise.api.entities.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary, Integer> {

    @Override
    Optional<TransactionSummary> findById(Integer customerId);
}
