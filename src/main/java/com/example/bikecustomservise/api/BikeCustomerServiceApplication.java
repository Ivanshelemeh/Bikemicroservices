package com.example.bikecustomservise.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EnableEurekaClient
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = {"com.example.bikecustomservise.api",
        "com.example.bikecustomservise.api.service.login"})
public class BikeCustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeCustomerServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}