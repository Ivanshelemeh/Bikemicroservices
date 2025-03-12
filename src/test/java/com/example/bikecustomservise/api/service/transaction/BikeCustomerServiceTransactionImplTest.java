package com.example.bikecustomservise.api.service.transaction;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.entities.CustomerTransaction;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.transaction.TransactionFindModel;
import com.example.bikecustomservise.api.repos.transaction.BikeCustomerTransaction;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BikeCustomerServiceTransactionImplTest {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime END_TIME = LocalDateTime.now().plusMinutes(30);

    private static final TransactionFindModel FIND_MODEL = new TransactionFindModel(
            12,
            NOW,
            END_TIME
    );
    private static final TransactionFindModel ILLEGAL_MODEL = new TransactionFindModel(
            10,
            null,
            null
    );
    private static final CustomerTransaction CUSTOMER_TRANSACTION = new CustomerTransaction(
            22L,
            NOW,
            new BikeCustomer(),
            CustomerTransaction.TransactionStatus.PASS

    );

    private static final BikeCustomerModel CUSTOMER_MODEL = new BikeCustomerModel(
            "Josh",
            "long1990@gmail.com",
            "11234UtotoASd"
    );

    @Mock
    private BikeCustomerMapper mapper;

    @Mock
    private BikeCustomerTransaction transaction;

    @InjectMocks
    private BikeCustomerServiceTransactionImpl serviceTransactionImpl;

    @BeforeEach
    void updateUp() {
        serviceTransactionImpl = null;
        MockitoAnnotations.openMocks(this);
        Mockito.when(transaction.findTransactions(Mockito.anyInt(),
                        Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(List.of(CUSTOMER_TRANSACTION));
        Mockito.when(mapper.mapFromCustomerEntity(Mockito.any()))
                .thenReturn(CUSTOMER_MODEL);


    }

    @Test
    void get_customer_transactions_happyPath() {
        final var actualResult = serviceTransactionImpl.getCustomerTransactionsFrom(FIND_MODEL);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(actualResult),
                () -> Assertions.assertEquals(1, actualResult.size())

        );
    }

    @Test
    void get_customer_when_periods_transaction_not_set_fails() {
        Mockito.when(transaction.findTransactions(Mockito.anyInt(),
                        Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> serviceTransactionImpl.getCustomerTransactionsFrom(ILLEGAL_MODEL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("customer's transactions should be set");
    }


}