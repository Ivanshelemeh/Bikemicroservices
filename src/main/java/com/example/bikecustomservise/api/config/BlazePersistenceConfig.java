package com.example.bikecustomservise.api.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBlazerRepositories(basePackages = "com.example.bikecustomservise.api.repos")
@RequiredArgsConstructor
public class BlazePersistenceConfig {

    private final JpaConfig jpaConfig;


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CriteriaBuilderFactory createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        return config.createCriteriaBuilderFactory(entityManagerFactory());
    }

    public EntityManagerFactory entityManagerFactory() {
        return jpaConfig.entityManagerFactory();
    }

}
