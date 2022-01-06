package com.example.bikecustomservise.api.utilit;

import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;
import lombok.Data;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import static com.example.bikecustomservise.api.utilit.BikeCustomerMapper.MODEL_MAPPER;

@Component
@Data
public class BikeOrderMapper {

    public BikeOrderDTO mapToOrderDto(BikeOrder order) {
        return MODEL_MAPPER.map(order, BikeOrderDTO.class);
    }
}
