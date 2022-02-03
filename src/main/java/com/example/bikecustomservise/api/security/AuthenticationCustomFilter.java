package com.example.bikecustomservise.api.security;

import com.example.bikecustomservise.api.dto.BikeCustomerSharedDTO;
import com.example.bikecustomservise.api.dto.BikeCustomerSingInModel;
import com.example.bikecustomservise.api.service.BikeLogInService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationCustomFilter extends UsernamePasswordAuthenticationFilter {

    private BikeLogInService service;
    private Environment environment;

    @Autowired
    public AuthenticationCustomFilter(BikeLogInService service, Environment environment,
                                      AuthenticationManager authenticationManager) {
        this.service = service;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        BikeCustomerSingInModel singInModel = new ObjectMapper()
                .readValue(request.getInputStream(), BikeCustomerSingInModel.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        singInModel.getEmail(),
                        singInModel.getPassword(),
                        new ArrayList<>()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        BikeCustomerSharedDTO sharedDTO = service.getUserDetailsByEmail(userName);

        String token = Jwts.builder()
                .setSubject(String.valueOf(sharedDTO.getId()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration.time")))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        response.addHeader("token", token);
        response.addHeader("customerId", String.valueOf(sharedDTO.getId()));
    }
}
