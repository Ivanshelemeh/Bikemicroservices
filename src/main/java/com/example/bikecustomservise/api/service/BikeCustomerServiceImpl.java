package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import com.example.bikecustomservise.api.service.BikeCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BikeCustomerServiceImpl implements BikeCustomerService {

    private final BikeCustomerRepository bikeCustomerRepository;


    @Override
    public List<BikeCustomer> findAll() {
        return bikeCustomerRepository.findAll();
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
    public void save(BikeCustomer customer) {

        if (customer == null) {
            throw new NoSuchFieldException("Not found");
        }
        bikeCustomerRepository.save(customer);
    }

    @SneakyThrows
    public void updateNickName(String name, BikeCustomer bikeCustomer) {
        if (name == null) {
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
            log.error("id could not be empty" + id);
            throw new NoSuchFieldException("not found id");
        }
        bikeCustomerRepository.deleteBikeCustomerById(id);

    }
}