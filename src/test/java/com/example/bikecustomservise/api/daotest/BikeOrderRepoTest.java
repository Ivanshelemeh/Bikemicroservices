package com.example.bikecustomservise.api.daotest;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.entities.OrderDetails;
import com.example.bikecustomservise.api.repos.order.BikeOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Profile("test")
@TestPropertySource("/application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class BikeOrderRepoTest {

    @Autowired
    public BikeOrderRepository bikeOrderRepository;

    @Test
    @Sql("/insert-bike_order.sql")
    public void test_bikeOrderRepoCreate(){

        BikeOrder bikeOrder = new BikeOrder();
        bikeOrder.setNameOrder("TrailBike");
        bikeOrder.setPriceOrder(22.44);
        bikeOrder.setOrderDetails(new OrderDetails(
                12,
                "Any description"
        ));
        bikeOrder.setVersion(20);
        bikeOrder.setCreatedAt(Instant.now().plusSeconds(10000));
        bikeOrder.setLastModified(Instant.now().plusSeconds(200000));
        bikeOrder.setOrderType(BikeOrder.OrderType.DETAIL);
        bikeOrder.setPremiumOrder(BikeOrder.PremiumOrder.TRUE);
        bikeOrder.setCustomers(List.of(new BikeCustomer()));

        final var saveOrder = bikeOrderRepository.save(bikeOrder);

        assertThat(saveOrder).isNotNull();
        assertThat(saveOrder.getOrderDetails().getOrderInfo()).isEqualTo("Any description");

    }
}
