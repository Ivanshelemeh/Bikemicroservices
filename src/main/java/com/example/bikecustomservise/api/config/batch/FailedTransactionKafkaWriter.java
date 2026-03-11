package com.example.bikecustomservise.api.config.batch;

import com.example.bikecustomservise.api.dto.analyze.FailedTransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FailedTransactionKafkaWriter implements ItemWriter<FailedTransactionEvent> {

    private final KafkaTemplate<String, FailedTransactionEvent> kafkaTemplate;
    private final String topicName;

    @Override
    public void write(List<? extends FailedTransactionEvent> items) {
        for (FailedTransactionEvent event : items) {
            kafkaTemplate.send(topicName, String.valueOf(event.transactionId()), event);
            log.debug("Sent failed transaction event to topic={}, transactionId={}", topicName, event.transactionId());
        }
        log.info("Sent {} failed transaction events to topic {}", items.size(), topicName);
    }
}
