package com.example.bikecustomservise.api.controllertest;

import com.example.bikecustomservise.api.service.BikeCustomerServiceImpl;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BikeCustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BikeCustomerServiceImpl bikeCustomerService;

    @MockBean
    private BikeCustomerMapper mapper;

    @SneakyThrows
    @Test
    void testGetAllCustomers() {
        mockMvc.perform(get("/customer/list")
                .contentType("application/json")
        ).andExpect(status().isOk());
    }

}