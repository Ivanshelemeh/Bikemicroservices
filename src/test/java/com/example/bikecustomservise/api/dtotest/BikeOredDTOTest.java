package com.example.bikecustomservise.api.dtotest;

import com.example.bikecustomservise.api.dto.order.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;
 class BikeOredDTOTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void when_Convert_toDTO() {
        BikeOrder order = new BikeOrder();
        order.setNameOrder("transition");
        order.setPriceOrder(10.99);

        BikeOrderDTO dto = modelMapper.map(order, BikeOrderDTO.class);
        assertEquals(order.getNameOrder(), dto.orderName());
    }
}
