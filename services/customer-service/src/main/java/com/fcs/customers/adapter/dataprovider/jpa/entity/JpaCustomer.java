package com.fcs.customers.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "customers")
@Data
public class JpaCustomer {

    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    private String document;
    private String name;
    private String password;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private JpaAddress address;
}
