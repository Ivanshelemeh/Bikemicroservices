package com.example.bikecustomservise.api.rest;

import com.example.bikecustomservise.api.dto.BikeCustomerDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.service.BikeCustomerServiceImpl;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class BikeCustomerController {

    private final BikeCustomerServiceImpl service;
    private final BikeCustomerMapper customerMapper;


    @Autowired
    public BikeCustomerController(BikeCustomerServiceImpl service, BikeCustomerMapper customerMapper) {
        this.service = service;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/list")
    public List<BikeCustomerDTO> customerDTOList() {
        List<BikeCustomerDTO> bikeCustomerDTOS = service.findAll().stream().map(customerMapper::mapToDTO).collect(Collectors.toList());
        return bikeCustomerDTOS;

    }

    @PostMapping(value = "/bike", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BikeCustomer> postDto(@Validated @RequestBody BikeCustomerDTO dto) {
        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        BikeCustomer bikeCustomer = customerMapper.mapToEntity(dto);
        service.save(bikeCustomer);
        return ResponseEntity.ok(bikeCustomer);

    }

    @PutMapping(value = "/update/{nickName}", consumes = {MediaType.APPLICATION_JSON_VALUE}
    ,produces = {MediaType.APPLICATION_JSON_VALUE})
    @SneakyThrows
    public ResponseEntity<String> updateCustomer( @PathVariable("nickName") String nickName, @RequestBody @Validated BikeCustomer customer) {
        if (nickName.isEmpty()) {
            ResponseEntity.noContent().build();
        }
       this.service.updateNickName(nickName,customer);
        return new ResponseEntity<>("changed",HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BikeCustomer> deleteCustomer(@PathVariable Integer id) {
        service.removeBikeCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
