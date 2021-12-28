package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BikeCustomerServiceImpl implements BikeCustomerService {

    private final BikeCustomerRepository bikeCustomerRepository;


    @Autowired
    public BikeCustomerServiceImpl(BikeCustomerRepository bikeCustomerRepository) {
        this.bikeCustomerRepository = bikeCustomerRepository;
    }


    @Override
    public List<BikeCustomer> findAll() {
        return bikeCustomerRepository.findAll()
                .stream().collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    @Transactional
    public BikeCustomer findOne(Integer id) {
        Optional<BikeCustomer> optional = Optional.of(new BikeCustomer());
        if (optional.isPresent()) {
            BikeCustomer customer = optional.get();
            bikeCustomerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Override
    public void deleteBikeCustomerById(Integer id) {
        BikeCustomer bikeCustomer = bikeCustomerRepository.findBikeCustomerById(id);
        bikeCustomerRepository.delete(bikeCustomer);

    }

    @Override
    @SneakyThrows
    public BikeCustomer save(BikeCustomer customer) {
        if (customer == null) {
            throw new NoSuchFieldException("Not found");
        }
        bikeCustomerRepository.save(customer);
        return customer;
    }

    @SneakyThrows
    public void updateNickName(String name, BikeCustomer bikeCustomer) {
        if (name.isEmpty()) {
            throw new NoSuchFieldException("not such name found");
        }
        BikeCustomer bikeCustomer1 = new BikeCustomer();
        bikeCustomer1.setNickName(name);
        bikeCustomer1.setPassword(bikeCustomer.getPassword());
        bikeCustomer1.setEmail(bikeCustomer.getEmail());
        bikeCustomerRepository.save(bikeCustomer1);
    }

    @SneakyThrows
    public void removeBikeCustomer(Integer id) {
        if (id == null) {
            log.error("Not such id could be empty {}" + id);
            throw new NoSuchFieldException("not found id");
        }
        bikeCustomerRepository.deleteBikeCustomerById(id);

    }
}
