package com.example.bikecustomservise.api.model;

public record PageRq(Integer size,
                     Integer page) {


    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }
}
