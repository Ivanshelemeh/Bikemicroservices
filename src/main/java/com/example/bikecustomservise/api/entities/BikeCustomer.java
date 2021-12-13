package com.example.bikecustomservise.api.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bike_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BikeCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true, name = "nickname")
    private String nickName;

    @Column(name = "mail",nullable = false)
    @Size(max = 20)
    @Email(regexp = "^([\\\\w-\\\\.]+){1,64}@([\\\\w&&[^_]]+){2,255}.[a-z]{2,}$")
    private String email;

    @Column(name = "password")
    private String password;
}
