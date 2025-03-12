package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.entities.CustomerTransaction;
import com.example.bikecustomservise.api.model.transaction.TransactionFindModel;
import com.example.bikecustomservise.api.model.transaction.TransactionalModel;
import com.example.bikecustomservise.api.repos.transaction.BikeCustomerTransaction;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeCustomerServiceTransactionImpl implements BikeCustomerServiceTransaction {

    private static final String TRANSACTIONS_ERROR_MSG = "customer's transactions periods should be set";

    private final BikeCustomerTransaction customerTransaction;
    private final BikeCustomerMapper mapper;

    @Override
    public Set<TransactionalModel> getCustomerTransactionsFrom(TransactionFindModel findModel) {
        if (ObjectUtils.isEmpty(findModel.startPeriod()) ||
                ObjectUtils.isEmpty(findModel.endPeriod())) {
            log.debug("transaction periods not set");
            throw new IllegalArgumentException(TRANSACTIONS_ERROR_MSG);
        }
        return customerTransaction.findTransactions(
                        findModel.customerId(),
                        findModel.startPeriod(),
                        findModel.endPeriod()
                ).stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toSet());
    }

    private TransactionalModel mapFromEntity(@NonNull final CustomerTransaction transaction) {
        return new TransactionalModel(
                transaction.getPeriod(),
                mapper.mapFromCustomerEntity(transaction.getCustomer())
        );
    }
}
