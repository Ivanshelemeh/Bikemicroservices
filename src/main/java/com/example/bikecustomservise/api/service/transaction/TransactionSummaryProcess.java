package com.example.bikecustomservise.api.service.transaction;

import org.springframework.lang.NonNull;

public interface TransactionSummaryProcess {


    void publishSummary(@NonNull Integer customerId);
}
