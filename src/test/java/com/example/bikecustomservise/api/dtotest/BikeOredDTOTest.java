package com.example.bikecustomservise.api.dtotest;

import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeOredDTOTest {


    private ModelMapper modelMapper= new ModelMapper();

    @Test
    public void when_Convert_toDTO(){
        BikeOrder order = new BikeOrder();
        order.setNameOrder("transition");
        order.setPriceOrder(10.99);
        order.setId(12);

        BikeOrderDTO dto = modelMapper.map(order, BikeOrderDTO.class);
        assertEquals(order.getId(),dto.getId());
        assertEquals(order.getNameOrder(),dto.getNameOrder());
    }
}
