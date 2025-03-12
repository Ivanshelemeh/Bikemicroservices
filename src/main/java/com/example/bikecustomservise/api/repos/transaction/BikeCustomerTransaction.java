package com.example.bikecustomservise.api.repos.transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface BikeCustomerTransaction {

    List<com.example.bikecustomservise.api.entities.CustomerTransaction> findTransactions(Integer customerId,
                                                                                          LocalDateTime startTime,
                                                                                          LocalDateTime endTime);
}
