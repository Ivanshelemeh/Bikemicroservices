package com.example.bikecustomservise.api.model.order;

import com.example.bikecustomservise.api.model.PageRq;

public record OrderFindPricesModel(
        List<String> orderNames,
        PageRq pageRq
) {
}
