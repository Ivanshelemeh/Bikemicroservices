package com.example.bikecustomservise.api.security;

import com.example.bikecustomservise.api.service.BikeLogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class BikeCustomerSecureWeb extends WebSecurityConfigurerAdapter {


    private BikeLogInService logInService;
    private BCryptPasswordEncoder passwordEncoder;
    private Environment env;


    @Autowired
    public BikeCustomerSecureWeb(BikeLogInService logInService, BCryptPasswordEncoder passwordEncoder, Environment env) {
        this.logInService = logInService;
        this.passwordEncoder = passwordEncoder;
        this.env =env;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(logInService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/customer/**")
                .authenticated();
        http.authorizeHttpRequests().antMatchers("/register/**")
                .permitAll();
        http.authorizeHttpRequests().antMatchers("/access/add").permitAll().
                        and()
                .authorizeHttpRequests().antMatchers("/access/load").authenticated()
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationCustomFilter getAuthenticationFilter() throws Exception {
        AuthenticationCustomFilter filter = new AuthenticationCustomFilter(logInService,env,authenticationManager());
        return filter;
    }

}
