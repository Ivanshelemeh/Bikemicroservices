package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.dto.analyze.AnalyzeEventDto;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.repos.analyze.TransactionSummaryRepository;
import com.example.bikecustomservise.api.service.customer.BikeCustomerService;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.TRANSACTION_SUMMARY_NOT_SET;

@Service
@RequiredArgsConstructor
public class TransactionSummaryService implements TransactionSummaryProcess {

    private final TransactionSummaryRepository summaryRepository;
    private final BikeCustomerService customerService;
    private final BikeCustomerMapper mapper;
    private final KafkaTemplate<String, AnalyzeEventDto> kafkaTemplate;


    @SneakyThrows
    @Override
    @Transactional(value = "kafkaTransactionManager", rollbackFor = ServiceProccessingException.class)
    public void publishSummary(@NonNull final Integer customerId) {
        final var customer = mapper.mapFromModel(customerService.findCustomer(customerId));
        final var summary = summaryRepository.findTransactionSummaryByCustomerId(customer.getId())
                .orElseThrow(() -> new ServiceProccessingException(TRANSACTION_SUMMARY_NOT_SET));
        final var analyzeEvent = new AnalyzeEventDto(
                summary.bikeCustomerId().longValue(),
                summary.totalTransactions(),
                summary.totalOrderPrice().longValue()
        );
        try {
            kafkaTemplate.executeInTransaction(op -> {
                op.send("summary-topic", String.valueOf(analyzeEvent.customerId()), analyzeEvent);
                return true;
            });
        } catch (Exception e) {

        }
    }
}
