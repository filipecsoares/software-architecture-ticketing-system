package com.fcs.orders.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity(name = "RESERVED_ORDERS")
@Data
public class JpaReservedOrder {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer eventId;
    private Integer sessionId;
    private Integer roomId;

    @ElementCollection
    @MapKeyColumn(name = "ticket_by_chair_id")
    @Column(name = "tickets_by_chair")
    private Map<Integer, List<String>> ticketsByChairs;

    private Integer customerId;
    private Double totalPrice;

    private LocalDateTime creationDateTime;
}
