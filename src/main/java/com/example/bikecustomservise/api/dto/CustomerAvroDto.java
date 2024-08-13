package com.example.bikecustomservise.api.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

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
    @NotNull
    private String orderName;
    @NotNull
    private Double orderPrice;

}
