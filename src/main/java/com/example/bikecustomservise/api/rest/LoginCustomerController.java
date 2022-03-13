package com.example.bikecustomservise.api.rest;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerSingInModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
public class LoginCustomerController {

    @PostMapping("/attach")
    public ResponseEntity postAccess(@RequestBody BikeCustomerSingInModel dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);

    }
}
