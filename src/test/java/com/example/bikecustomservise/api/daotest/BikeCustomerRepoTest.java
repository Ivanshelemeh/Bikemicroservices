package com.example.bikecustomservise.api.daotest;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class BikeCustomerRepoTest {

    @Autowired
    BikeCustomerRepository bikeCustomerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DataSource source;

    @Test
    @Sql("/insert-test.sql")
    public void test_getOneBikeCustomer() throws Exception {
        BikeCustomer bikeCustomer = new BikeCustomer();
        bikeCustomer.setPassword("1234");
        bikeCustomer.setEmail("nemo041990@mail.ru");
        bikeCustomer.setNickName("Nemo");
        BikeCustomer saveBike = bikeCustomerRepository.save(bikeCustomer);

        Assertions.assertThat(saveBike.getEmail()).isNotNull();
    }
}