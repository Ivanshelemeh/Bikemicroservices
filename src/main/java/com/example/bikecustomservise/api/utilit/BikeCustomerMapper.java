package com.example.bikecustomservise.api.utilit;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Data
@Component
public class BikeCustomerMapper {

    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public BikeCustomerDTO mapToDTO(BikeCustomer customer) {
        BikeCustomerDTO dto = MODEL_MAPPER.map(customer, BikeCustomerDTO.class);
        return dto;
    }

    public BikeCustomer mapToEntity(BikeCustomerDTO customerDTO) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BikeCustomer customer = MODEL_MAPPER.map(customerDTO, BikeCustomer.class);
        customer.setNickName(customerDTO.getNickName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        return customer;
    }

}
