package com.fcs.customers.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "address")
@Data
public class JpaAddress {

    @Id
    @GeneratedValue
    private Integer id;
    private String street;
    private Integer num;
    private String zipCode;

    private String block;
    private Integer floor;
    private Integer number;
}
