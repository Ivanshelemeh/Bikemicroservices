package com.example.bikecustomservise.api.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "bike_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BikeCustomer implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true, name = "nickname")
    private String nickName;

    @Column(name = "mail",nullable = false, unique = true)
    @Size(max = 20)
    @Email
    private String email;

    @Column(name = "password")
    private String password;


    @ManyToOne(targetEntity = BikeOrder.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "id",insertable = false, updatable = false)
    private BikeOrder order;
}
