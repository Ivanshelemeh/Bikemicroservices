package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import com.example.bikecustomservise.api.model.order.OrderFindNamesModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import com.example.bikecustomservise.api.service.order.BikeOrderPremiumService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BikeOrderPremiumController.class)
class BikeOrderPremiumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BikeOrderPremiumService orderPremiumService;

    @Test
    void get_order_premium_list_success() throws Exception {
        //Arrange
        int page = 0;
        int size = 10;
        final OrderModel orderModelOne = new OrderModel(
                "ElectoroCleaner",
                22.55
        );
        final OrderModel orderModelTwo = new OrderModel(

                "WashMachine",
                30.44
        );

        final List<OrderModel> mockModels = List.of(orderModelOne, orderModelTwo);

        final List<BikeOrderDTO> orderDTOS = List.of(
                new BikeOrderDTO(orderModelOne.orderName(), orderModelOne.orderPrice()),
                new BikeOrderDTO(orderModelTwo.orderName(), orderModelTwo.orderPrice())
        );

        when(orderPremiumService
                .findPremiumOrder(any(OrderFindNamesModel.class)))
                .thenReturn(mockModels);

        //Act
        mockMvc.perform(get("/rest/api/v1/orders/premium/orders")
                        .param("pageOrder", String.valueOf(page))
                        .param("sizeOrder", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].orderName").value(orderDTOS.get(0).orderName()))
                .andExpect(jsonPath("$[0].priceOrder").value(orderDTOS.get(0).priceOrder()))
                .andExpect(jsonPath("$[1].orderName").value(orderDTOS.get(1).orderName()))
                .andExpect(jsonPath("$[1].priceOrder").value(orderDTOS.get(1).priceOrder()));

        ArgumentCaptor<OrderFindNamesModel> captor =
                ArgumentCaptor.forClass(OrderFindNamesModel.class);

        //Actual
        verify(orderPremiumService).findPremiumOrder(captor.capture());

    }
}