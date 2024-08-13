package com.example.bikecustomservise.api.model;

import java.util.List;

public record PageRs<T>(List<T> content,
                        int pageSize,
                        boolean hasNext,
                        int pageNumber,
                        int totalElements) {
}
