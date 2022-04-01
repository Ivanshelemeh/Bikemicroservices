package com.example.bikecustomservise.api.controllertest;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.service.BikeOrderServiceImpl;
import com.example.bikecustomservise.api.utilit.BikeOrderMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BikeOrderControllerTest {

    @MockBean
    private BikeOrderMapper bikeOrderMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BikeOrderServiceImpl orderService;

    @SneakyThrows
    @Test
    void getAllOrdersTest(){
        mockMvc.perform(get("/order/orders")
                .contentType("application/json")
        ).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void whenNull_thenReturn404(){
        BikeOrder order = new BikeOrder(2,null,22.34,null);
        mockMvc.perform(post("/order/add")
                .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }
}
