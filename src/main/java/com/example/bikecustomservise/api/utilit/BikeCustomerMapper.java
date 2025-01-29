package com.example.bikecustomservise.api.utilit;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerRequestModel;
import com.example.bikecustomservise.api.dto.BikeCustomerResponseModel;
import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.customer.BikeCustomerUpdateModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
public class BikeCustomerMapper {

    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public BikeCustomerDTO mapToDTO(BikeCustomer customer) {
        return MODEL_MAPPER.map(customer, BikeCustomerDTO.class);
    }

    public BikeCustomer mapToEntity(BikeCustomerDTO customerDTO) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BikeCustomer customer = MODEL_MAPPER.map(customerDTO, BikeCustomer.class);
        customer.setNickName(customerDTO.getNickName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        return customer;
    }

    public BikeCustomer mapFromModel(@NonNull final BikeCustomerModel model) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BikeCustomer bikeCustomer = MODEL_MAPPER.map(model, BikeCustomer.class);
        bikeCustomer.setNickName(model.nameCustomer());
        bikeCustomer.setPassword(model.customerPassword());
        bikeCustomer.setEmail(model.customerEmail());
        return bikeCustomer;
    }

    public BikeCustomer mapFromUpdateModel(@NonNull final BikeCustomerUpdateModel updateModel) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BikeCustomer bikeCustomer = MODEL_MAPPER.map(updateModel, BikeCustomer.class);
        bikeCustomer.setNickName(updateModel.name());
        bikeCustomer.setEmail(updateModel.email());
        return bikeCustomer;
    }

    public BikeCustomer mapToCustomerEntity(BikeCustomerSharedDTO sharedDTO) {
        BikeCustomer customer1 = MODEL_MAPPER.map(sharedDTO, BikeCustomer.class);
        customer1.setNickName(sharedDTO.getNameCustomer());
        customer1.setPassword(sharedDTO.getPassword());
        customer1.setId(sharedDTO.getId());
        return customer1;
    }

    public BikeCustomerSharedDTO mapToDToShared(BikeCustomer bikeCustomer) {
        return MODEL_MAPPER.map(bikeCustomer, BikeCustomerSharedDTO.class);
    }

    public BikeCustomerSharedDTO mapFromRequestModel(BikeCustomerRequestModel requestModel) {
        return MODEL_MAPPER.map(requestModel, BikeCustomerSharedDTO.class);
    }

    public BikeCustomerResponseModel mapFromSharedDTO(BikeCustomerSharedDTO sharedDTO) {
        return MODEL_MAPPER.map(sharedDTO, BikeCustomerResponseModel.class);
    }

}
