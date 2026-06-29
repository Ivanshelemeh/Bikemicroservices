package com.example.bikecustomservise.api.config;

import jakarta.activation.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages = {
        "com.example.bikecustomservise.api.repos.customer",
        "com.example.bikecustomservise.api.repos.order",
        "com.example.bikecustomservise.api.repos.transaction",
        "com.example.bikecustomservise.api.repos.analyze",
        "com.example.bikecustomservise.api.repos.premium"})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JpaConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUserName;
    @Value("${spring.datasource.password}")
    private String databasePassword;
    @Value("${spring.jpa.database-platform}")
    private String dbPlatform;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource data = new DriverManagerDataSource();
        data.setDriverClassName(driver);
        data.setUrl(databaseUrl);
        data.setUsername(databaseUserName);
        data.setPassword(databasePassword);
        return (DataSource) data;

    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory factory = entityManagerFactory();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setShowSql(Boolean.TRUE);
        vendorAdapter.setDatabasePlatform(dbPlatform);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.example.bikecustomservise.api.entities");
        factory.setDataSource((javax.sql.DataSource) dataSource());
        factory.afterPropertiesSet();
        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return factory.getObject();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

}
