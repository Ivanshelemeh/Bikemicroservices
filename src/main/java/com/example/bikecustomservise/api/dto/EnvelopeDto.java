package com.example.bikecustomservise.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class EnvelopeDto {

    private Integer version;
    private String singAlgo;
    private byte[] hash;
    private CustomerAvroDto customerAvroDto;

}
