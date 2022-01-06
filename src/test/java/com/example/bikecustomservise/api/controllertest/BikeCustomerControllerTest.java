package com.example.bikecustomservise.api.controllertest;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import com.example.bikecustomservise.api.service.BikeCustomerServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BikeCustomerControllerTest {

    @MockBean
    private BikeCustomerRepository repository;

    private BikeCustomerServiceImpl customerService;

    @Before
    public void setUp() {
        customerService = new BikeCustomerServiceImpl(repository);
    }

    @Test
    public void getCustomerList_ShouldReturnList() throws Exception {
        BikeCustomer customer = new BikeCustomer();
        customer.setId(22);
        customer.setEmail("lol041990@mail.ru");
        customer.setNickName("Miller");
        customer.setPassword("1234Qwe");
        repository.save(customer);
        given(repository.findBikeCustomerById(22)).willReturn(customer);

        BikeCustomer bikeCustomer = customerService.findOne(22);
        assertThat(bikeCustomer.getId()).isEqualTo(22);
    }
}
