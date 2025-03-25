package com.example.bikecustomservise.api.daotest;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.entities.CustomerTransaction;
import com.example.bikecustomservise.api.repos.customer.BikeCustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class BikeCustomerRepoTest {

    @Autowired
    BikeCustomerRepository bikeCustomerRepository;

    @Test
    @Sql("/insert-test.sql")
    public void test_getOneBikeCustomer() throws Exception {
        BikeCustomer bikeCustomer = new BikeCustomer();
        bikeCustomer.setPassword("Utoto3274ert");
        bikeCustomer.setEmail("nemo041990@mail.ru");
        bikeCustomer.setNickName("Josh");
        bikeCustomer.setPremiumCustomer(BikeCustomer.PremiumCustomer.TRUE);
        bikeCustomer.setCreatedAt(Instant.now().plusSeconds(3000));
        bikeCustomer.setLastModified(Instant.now().plusSeconds(60000));
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setCustomer(bikeCustomer);
        transaction.setPeriod(LocalDateTime.now());
        transaction.setStatus(CustomerTransaction.TransactionStatus.PASS);
        bikeCustomer.setTransactions(Set.of(transaction));
        BikeCustomer saveBike = bikeCustomerRepository.save(bikeCustomer);

        Assertions.assertThat(saveBike).isNotNull();
        Assertions.assertThat(saveBike.getEmail()).isEqualTo(bikeCustomer.getEmail());
        Assertions.assertThat(saveBike.getTransactions()).contains(transaction);
    }

}