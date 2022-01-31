package com.example.bikecustomservise.api.controllertest;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BikeCustomerControllerTest {

    @Autowired
    TestRestTemplate testClient;


    @Test
    void testGetCustomerDtoList() {
        List<BikeCustomerDTO> dtoList = new ArrayList<>();
        dtoList = (List<BikeCustomerDTO>) testClient
                .getForObject("/customer/list", BikeCustomerDTO.class);
        assertThat(dtoList).isNull();

    }
}
