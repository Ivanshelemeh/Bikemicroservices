package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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
    @SneakyThrows
    public BikeCustomerSharedDTO getUserDetailsByPassword(String password) {
        return Optional.ofNullable(repository.findByPassword(password))
                .map(mapper::mapToDToShared)
                .orElseThrow(()-> new NoSuchFieldException("not such user with"+ password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BikeCustomer bikeCustomer = repository.findByPassword(username);
        if (bikeCustomer == null) {
            throw new UsernameNotFoundException("The user has not been found");
        }
        return new User(bikeCustomer.getEmail(), bikeCustomer.getPassword(), true, true, true,
                true, new ArrayList<>());
    }
}
