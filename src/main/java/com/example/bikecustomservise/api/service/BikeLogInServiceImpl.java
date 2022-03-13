package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("bikeLoginService")
public class BikeLogInServiceImpl implements BikeLogInService {

    private final BikeCustomerRepository repository;
    private final BikeCustomerMapper mapper;
    private final BCryptPasswordEncoder encoder;

    public BikeLogInServiceImpl(BikeCustomerRepository repository, BikeCustomerMapper mapper, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public BikeCustomerSharedDTO create(BikeCustomerSharedDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        BikeCustomer customer = mapper.mapToCustomerEntity(dto);
        repository.save(customer);
        return mapper.mapToDToShared(customer);
    }

    @Override
    public BikeCustomerSharedDTO getUserDetailsByPassword(String password) {
        BikeCustomer bikeCustomer = repository.findByPassword(password);
        if (bikeCustomer == null) {
            throw new UsernameNotFoundException("no such user with item"+ password);
        }
        return mapper.mapToDToShared(bikeCustomer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BikeCustomer bikeCustomer = repository.findByPassword(username);
        if (bikeCustomer == null) {
            throw new UsernameNotFoundException("Not found user");
        }
        return new User(bikeCustomer.getEmail(), bikeCustomer.getPassword(), true, true, true,
                true, new ArrayList<>());
    }
}
