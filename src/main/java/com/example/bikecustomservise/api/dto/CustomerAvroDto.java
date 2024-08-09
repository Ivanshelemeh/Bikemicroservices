package com.example.bikecustomservise.api.dto;


import com.example.bikecustomservise.api.entities.BikeOrder;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Builder(access = AccessLevel.PUBLIC)
@ToString
public class CustomerAvroDto {

    @NonNull
    private OffsetDateTime dateTime;
    @NotNull
    private String customerName;
    @NotNull
    private String customerEmail;

    private Set<BikeOrder> orders;

}
