package com.example.bikecustomservise.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BikeCustomServiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeCustomServiseApplication.class, args);
    }

}
