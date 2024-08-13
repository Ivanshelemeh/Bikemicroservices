package com.example.bikecustomservise.api.model;

import java.util.List;

public record PageDtoRs<T>(List<T> list,
                           int pageSize,
                           boolean hasNext,
                           int pageNumber,
                           int totalElements) {
}
