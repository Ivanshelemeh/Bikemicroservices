package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.dto.analyze.AnalyzeEventDto;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.repos.analyze.TransactionSummaryRepository;
import com.example.bikecustomservise.api.service.customer.BikeCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.TRANSACTION_SUMMARY_NOT_SET;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionSummaryService implements TransactionSummaryProcess {

    private final TransactionSummaryRepository summaryRepository;
    private final BikeCustomerService customerService;
    private final KafkaTemplate<String, AnalyzeEventDto> kafkaTemplate;


    @SneakyThrows
    @Override
    @Transactional(value = "kafkaTransactionManager", rollbackFor = ServiceProccessingException.class)
    public void publishSummary(@NonNull final Integer customerId) {
        final var customer = customerService.findCustomer(customerId);
        final var summary = summaryRepository.findTransactionSummaryByCustomerId(customer.customerId())
                .orElseThrow(() -> new ServiceProccessingException(TRANSACTION_SUMMARY_NOT_SET));
        final var analyzeEvent = new AnalyzeEventDto(
                summary.bikeCustomerId().longValue(),
                summary.totalTransactions(),
                summary.totalOrderPrice().longValue()
        );
        log.info(" Prepare and send analyze summary into topic kafka");
        try {
            kafkaTemplate.executeInTransaction(op -> {
                op.send("summary-topic", String.valueOf(analyzeEvent.customerId()), analyzeEvent);
                return true;
            });
        } catch (Exception e) {
            log.error("Sending analyze summary failed = {}", e.getCause());

        }
    }
}
