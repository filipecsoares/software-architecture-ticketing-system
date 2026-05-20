package com.fcs.tickets.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "tickets_type")
@Data
public class JpaTicketType {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}
