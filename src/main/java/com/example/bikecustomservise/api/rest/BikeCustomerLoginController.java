package com.example.bikecustomservise.api.rest;

import com.example.bikecustomservise.api.dto.BikeCustomerRequestModel;
import com.example.bikecustomservise.api.dto.BikeCustomerResponseModel;
import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.service.BikeCustomerServiceImpl;
import com.example.bikecustomservise.api.service.BikeLogInServiceImpl;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class BikeCustomerLoginController {

    private final BikeLogInServiceImpl logInService;
    private final BikeCustomerMapper bikeCustomerMapper;

    @Autowired
    private Environment env;

    @Autowired
    public BikeCustomerLoginController(BikeLogInServiceImpl logInService, BikeCustomerMapper bikeCustomerMapper) {
        this.logInService = logInService;
        this.bikeCustomerMapper = bikeCustomerMapper;
    }

    @RequestMapping(value = "/login"
            , produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        return "Work in port" + env.getProperty("server.port");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BikeCustomerResponseModel> createCustomer(@RequestBody BikeCustomerRequestModel model) {
        BikeCustomerSharedDTO sharedDTO = bikeCustomerMapper.mapFromRequestModel(model);
        BikeCustomerSharedDTO dto = logInService.create(sharedDTO);
        BikeCustomerResponseModel responseModel = bikeCustomerMapper.mapFromSharedDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);

    }
}
