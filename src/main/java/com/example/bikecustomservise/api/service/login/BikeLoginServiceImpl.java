package com.example.bikecustomservise.api.service.login;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.repos.customer.BikeCustomerRepository;
import com.example.bikecustomservise.api.utilit.BikeCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.USER_PARAMS_INCORRECT;

@Service("bikeLoginServiceImpl")
@RequiredArgsConstructor
public class BikeLoginServiceImpl implements BikeLoginService {

    private final BikeCustomerRepository repository;
    private final BikeCustomerMapper mapper;
    private final BCryptPasswordEncoder encoder;

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
                .orElseThrow(()-> new ServiceProccessingException(USER_PARAMS_INCORRECT));
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
