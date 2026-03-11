package com.example.bikecustomservise.api.exception;

public class OrderRecommendationProcessException  extends RuntimeException{
    public OrderRecommendationProcessException(String errorMessage){
        super(errorMessage);
    }
}
