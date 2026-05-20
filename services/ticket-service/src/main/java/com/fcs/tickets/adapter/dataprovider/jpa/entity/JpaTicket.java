package com.fcs.tickets.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "tickets")
@Data
public class JpaTicket {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String currency;
    private String unitPrice;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private JpaTicketType ticketType;
}
