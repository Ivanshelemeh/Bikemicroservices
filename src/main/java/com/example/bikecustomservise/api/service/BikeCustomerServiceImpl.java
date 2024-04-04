package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.annotation.AsyncRunnerAnnotation;
import com.example.bikecustomservise.api.entities.BikeCustomer;
import com.example.bikecustomservise.api.exception.ApplicationErrorEnum;
import com.example.bikecustomservise.api.exception.ServiceProccessingException;
import com.example.bikecustomservise.api.repos.BikeCustomerRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.EMPTY_CUSTOMER_NAME;
import static com.example.bikecustomservise.api.exception.ApplicationErrorEnum.INCORRECT_INPUT;

@Service
@Slf4j
public class BikeCustomerServiceImpl implements BikeCustomerService {

    private final BikeCustomerRepository bikeCustomerRepository;


    @Autowired
    public BikeCustomerServiceImpl(BikeCustomerRepository bikeCustomerRepository) {
        this.bikeCustomerRepository = bikeCustomerRepository;
    }


    @Override
    @Cacheable(value = "redisCache")
    public List<BikeCustomer> findAll() {
        return bikeCustomerRepository.findAll();

    }

    @Override
    @SneakyThrows
    @Transactional
    @CachePut(value = "redisCache", key = "#id")
    public BikeCustomer findOne(Integer id) {
        Optional<BikeCustomer> optional = Optional.of(new BikeCustomer());
        BikeCustomer customer = optional.get();
        bikeCustomerRepository.save(customer);
        return customer;
    }

    @Override
    @CacheEvict(value = "redisCache", key = "#id")
    public void deleteBikeCustomerById(Integer id) {
        BikeCustomer bikeCustomer = bikeCustomerRepository.findBikeCustomerById(id);
        bikeCustomerRepository.delete(bikeCustomer);

    }

    @Override
    @SneakyThrows
    @AsyncRunnerAnnotation
    public BikeCustomer save(BikeCustomer customer) {
        if (customer == null) {
            log.error("a customer is not found ");
            throw new ServiceProccessingException(INCORRECT_INPUT);
        }
        bikeCustomerRepository.save(customer);
        return customer;
    }

    @SneakyThrows
    @CachePut(value = "redisCache",key = "#bikeCustomer.nickName")
    public void updateNickName(String name, BikeCustomer bikeCustomer) {
        if (name == null || name.isEmpty()) {
            throw new ServiceProccessingException(EMPTY_CUSTOMER_NAME);
        }
        bikeCustomerRepository.deleteAll();
        BikeCustomer bikeCustomer1 = new BikeCustomer();
        bikeCustomer1.setNickName(name);
        bikeCustomer1.setPassword(bikeCustomer.getPassword());
        bikeCustomer1.setEmail(bikeCustomer.getEmail());
        bikeCustomerRepository.save(bikeCustomer1);
    }

}
