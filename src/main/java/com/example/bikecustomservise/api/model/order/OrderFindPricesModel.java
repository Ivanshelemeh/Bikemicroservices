package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.model.PageRq;

import java.util.List;

public record OrderFindPricesModel(
        List<String> orderNames,
        PageRq pageRq
) {
}
