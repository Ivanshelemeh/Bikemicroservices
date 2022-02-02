package com.example.bikecustomservise.api.utilit;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<BikeCustomer> mapToListCustomer(List<BikeCustomerDTO> customerDTOList) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<BikeCustomer> list = new ArrayList<>();
        list.forEach(bikeCustomer -> customerDTOList.add(MODEL_MAPPER.map(bikeCustomer, BikeCustomerDTO.class)));
        return list;
    }

    public BikeCustomer mapToCustomerEntity(BikeCustomerSharedDTO sharedDTO) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BikeCustomer customer1 = MODEL_MAPPER.map(sharedDTO, BikeCustomer.class);
        customer1.setNickName(sharedDTO.getNameCustomer());
        customer1.setPassword(sharedDTO.getPassword());
        customer1.setId(sharedDTO.getId());
        return customer1;
    }

    public BikeCustomerSharedDTO mapToDToShared(BikeCustomer bikeCustomer) {
        BikeCustomerSharedDTO dto = MODEL_MAPPER.map(bikeCustomer, BikeCustomerSharedDTO.class);
        return dto;
    }

}
