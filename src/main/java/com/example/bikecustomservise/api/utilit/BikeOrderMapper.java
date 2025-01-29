package com.example.bikecustomservise.api.utilit;

import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.model.order.OrderCreateModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class BikeOrderMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public BikeOrder mapFromModel(final OrderCreateModel createModel) {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return MODEL_MAPPER.map(createModel, BikeOrder.class);
    }

}
