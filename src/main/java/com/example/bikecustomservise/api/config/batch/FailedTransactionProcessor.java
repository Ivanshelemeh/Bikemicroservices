package com.example.bikecustomservise.api.config.batch;

import com.example.bikecustomservise.api.dto.analyze.FailedTransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;
import java.util.Map;

@Slf4j
public class FailedTransactionProcessor implements ItemProcessor<Map<String, Object>, FailedTransactionEvent> {

    @Override
    public FailedTransactionEvent process(Map<String, Object> item) {
        Long transactionId = ((Number) item.get("id")).longValue();
        Long customerId = ((Number) item.get("customer_id")).longValue();
        String ownerTransaction = (String) item.get("owner_transaction");
        String descriptionDetail = (String) item.get("description_detail");

        Object periodRaw = item.get("period_transaction");
        String period = periodRaw instanceof Timestamp
                ? ((Timestamp) periodRaw).toLocalDateTime().toString()
                : String.valueOf(periodRaw);

        if (descriptionDetail == null || descriptionDetail.isBlank()) {
            log.warn("Skipping transaction {} — empty description_detail", transactionId);
            return null;
        }

        log.debug("Processing failed transaction id={}, customerId={}", transactionId, customerId);

        return new FailedTransactionEvent(
                transactionId,
                customerId,
                ownerTransaction,
                descriptionDetail,
                period
        );
    }
}
