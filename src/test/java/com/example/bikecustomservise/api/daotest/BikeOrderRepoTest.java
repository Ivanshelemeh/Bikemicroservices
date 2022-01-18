package com.example.bikecustomservise.api.daotest;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.repos.BikeOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource("/application-test.properties")
@RunWith(SpringRunner.class)
public class BikeOrderRepoTest {

    @Autowired
    public BikeOrderRepository bikeOrderRepository;

    @Autowired
    public TestEntityManager entityManager;

    @Test
    @Sql("/insert-bike_order.sql")
    public void test_bikeOrderRepoCreate(){
        BikeOrder bikeOrder = new BikeOrder();
        bikeOrder.setNameOrder("TrailBike");
        bikeOrder.setPriceOrder(22.44);
        bikeOrder.setId(12);
        BikeOrder saveOrder = bikeOrderRepository.save(bikeOrder);

        assertThat(saveOrder.getPriceOrder()).isNotNull();

    }
}
