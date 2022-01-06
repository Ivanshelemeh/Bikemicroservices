package com.example.bikecustomservise.api.dtotest;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BikeCustomerConvertDTOTest {

    private BikeCustomerMapper mapper = new BikeCustomerMapper();
    
    @Test
    public void convert_mustBe_success(){
        BikeCustomer bikeCustomer = new BikeCustomer();
        bikeCustomer.setEmail("neo041990@gmail.com");
        bikeCustomer.setPassword("327412Ert");
        bikeCustomer.setId(22);

        BikeCustomerDTO dto = mapper.mapToDTO(bikeCustomer);
        assertEquals(bikeCustomer.getEmail(),dto.getEmail());
        assertFalse(dto.getPassword().isEmpty());
    }
}
