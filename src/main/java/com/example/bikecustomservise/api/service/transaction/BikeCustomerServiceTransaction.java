package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.model.PageRs;
import com.example.bikecustomservise.api.model.transaction.TransactionFindModel;
import com.example.bikecustomservise.api.model.transaction.TransactionalModel;
import com.example.bikecustomservise.api.model.transaction.TransactionalPeriodFindModel;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface BikeCustomerServiceTransaction {

    Set<TransactionalModel> getCustomerTransactionsFrom(@NonNull final TransactionFindModel findModel);

    PageRs<TransactionalModel> getTransactionsFromPeriods(@NonNull final TransactionalPeriodFindModel periodFindModel);


}
