package com.example.bikecustomservise.api.security;

import com.example.bikecustomservise.api.service.BikeLogInService;
import com.example.bikecustomservise.api.service.BikeLogInServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration for security  app via
 * custom authentication filter
 *
 * @author shele
 */
@Configuration
@EnableWebSecurity
public class BikeCustomerSecureWeb extends WebSecurityConfigurerAdapter {


    private BikeLogInServiceImpl bikeLogInService;
    private BCryptPasswordEncoder passwordEncoder;
    private Environment env;


    @Autowired
    public BikeCustomerSecureWeb(BikeLogInServiceImpl bikeLogInService, BCryptPasswordEncoder passwordEncoder, Environment env) {
        this.bikeLogInService = bikeLogInService;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(bikeLogInService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/customer/**")
                .permitAll();
        http.authorizeHttpRequests().antMatchers("/register/**")
                .permitAll();
        http.authorizeHttpRequests().antMatchers("/access/**").permitAll();
        http.addFilterBefore(getAuthenticationFilter(),AuthenticationCustomFilter.class)
                        .authorizeHttpRequests()
                                .antMatchers("/access/attach").authenticated();
       http.headers().frameOptions().disable();
    }

    private AuthenticationCustomFilter getAuthenticationFilter() throws Exception {
        AuthenticationCustomFilter filter = new AuthenticationCustomFilter(bikeLogInService, env, authenticationManager());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
