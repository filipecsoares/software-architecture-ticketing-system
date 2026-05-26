package com.fcs.orders.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity(name = "ORDER_ITEMS")
@Data
public class JpaOrderItem {

    @Id
    @GeneratedValue
    private final Integer id;
    private final Integer eventId;
    private final Integer sessionId;
    private final Integer roomId;

    @ElementCollection
    @MapKeyColumn(name = "ticket_by_chair_id")
    @Column(name = "tickets_by_chair")
    private final Map<Integer, String> ticketsByChairs;

    @OneToOne
    private final JpaOrder order;
}
