package com.example.bikecustomservise.api.model;

import jakarta.annotation.Nullable;

import java.util.List;

public record PageRs<T>(List<T> content,
                        int pageSize,
                        boolean hasNext,
                        @Nullable
                        int pageNumber) {
}
