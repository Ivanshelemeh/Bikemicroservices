package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.dto.analyze.AnalyzeEventDto;
import com.example.bikecustomservise.api.entities.TransactionSummary;
import com.example.bikecustomservise.api.repos.analyze.TransactionSummaryRepository;
import com.example.bikecustomservise.api.service.customer.BikeCustomerService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
@EmbeddedKafka(topics = {"summary-topic"}, partitions = 1)
@Profile("kafka")
class TransactionSummaryServiceTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaTemplate<String, AnalyzeEventDto> kafkaTemplate;

    @Autowired
    private TransactionSummaryService transactionSummaryService;

    @Autowired
    private TransactionSummaryRepository summaryRepository;

    @Autowired
    private BikeCustomerService bikeCustomerService;

    private Consumer<String, AnalyzeEventDto> consumer;

    @BeforeEach
    void setUp() {

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "test-group", "true", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, AnalyzeEventDto> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = consumerFactory.createConsumer();
        consumer.subscribe(Collections.singleton("summary-topic"));
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void should_publish_successfully() {

        Integer customerId = 1;
        Long expectedCustomerId = 1L;
        Integer expectedTotalTransactions = 5;
        Long expectedTotalPrice = 1000L;

        // Mock repository response
        when(summaryRepository.findTransactionSummaryByCustomerId(customerId))
                .thenReturn(Optional.of(new TransactionSummary(
                        customerId,
                        expectedTotalPrice.doubleValue(),
                        expectedTotalTransactions.longValue())));

        // When
        transactionSummaryService.publishSummary(customerId);

        // Then verify message was published
        ConsumerRecords<String, AnalyzeEventDto> records =
                KafkaTestUtils.getRecords(consumer);

        assertFalse(records.isEmpty());
        ConsumerRecord<String, AnalyzeEventDto> record =
                records.iterator().next();

        assertEquals(String.valueOf(expectedCustomerId), record.key());
        AnalyzeEventDto event = record.value();
        assertEquals(expectedCustomerId, event.customerId());
        assertEquals(expectedTotalTransactions, event.customerTransactions());
        assertEquals(expectedTotalPrice, event.amountOfOrders());

    }

}

