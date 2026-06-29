package com.example.bikecustomservise.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public  class TransactionDetails {

    private String ownerTransaction;
    private String descriptionTransaction;


}
