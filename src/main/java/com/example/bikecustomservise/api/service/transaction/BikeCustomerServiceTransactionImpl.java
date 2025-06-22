package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.entities.CustomerTransaction;
import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.transaction.TransactionFindModel;
import com.example.bikecustomservise.api.model.transaction.TransactionalModel;
import com.example.bikecustomservise.api.model.transaction.TransactionalPeriodFindModel;
import com.example.bikecustomservise.api.repos.transaction.CustomerTransactionRepository;
import com.example.bikecustomservise.api.repos.transaction.TransactionSpecifiactions;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeCustomerServiceTransactionImpl implements BikeCustomerServiceTransaction {

/*    private static final Integer THREAD_POOL_SIZE = 5;*/
    private static final String TRANSACTIONS_ERROR_MSG = "customer's transactions periods should be set";

    private final CustomerTransactionRepository transactionRepository;
    private final BikeCustomerMapper mapper;

    @Override
    public Set<TransactionalModel> getCustomerTransactionsFrom(TransactionFindModel findModel) {
        if (ObjectUtils.isEmpty(findModel.customerId())) {
            throw new IllegalArgumentException(TRANSACTIONS_ERROR_MSG);
        }
        return transactionRepository.findAllCustomerTransaction(
                        findModel.customerId()
                ).stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public PageRs<TransactionalModel> getTransactionsFromPeriods(@NonNull TransactionalPeriodFindModel periodFindModel) {
        final var transactionPages = transactionRepository.findAll(buildSpecification(periodFindModel), PageRequest.of(
                periodFindModel.request().getSize(),
                periodFindModel.request().getPage()
        ));
        return new PageRs<>(transactionPages.getContent()
                .stream()
                .map(this::mapFromEntity)
                .toList(),
                transactionPages.getSize(),
                transactionPages.hasNext(),
                transactionPages.getNumber(),
                Math.toIntExact(transactionPages.getTotalElements())
        );

    }

    private TransactionalModel mapFromEntity(@NonNull final CustomerTransaction transaction) {
        return new TransactionalModel(
                transaction.getPeriod(),
                mapper.mapFromCustomerEntity(transaction.getCustomer())
        );
    }

    private Specification<CustomerTransaction> buildSpecification(TransactionalPeriodFindModel periodFindModel) {
        Specification<CustomerTransaction> spec = Specification.where(null);

        spec = spec.and(TransactionSpecifiactions.hasPeriodTransactionsInRange(
                periodFindModel.startPeriod(), periodFindModel.endPeriod()));

        if (!StringUtils.isEmpty(periodFindModel.status())) {
            spec = spec.and(TransactionSpecifiactions.hasTransactionsCategory(periodFindModel.status()));
        }

        return spec;
    }

   /* private final Lazy<ExecutorService> executorService = Lazy.of(() ->
            Executors.newFixedThreadPool(THREAD_POOL_SIZE, r -> {
                Thread t = new Thread(r, "transaction-worker");
                t.setDaemon(true);
                return t;
            })
    );*/
}
