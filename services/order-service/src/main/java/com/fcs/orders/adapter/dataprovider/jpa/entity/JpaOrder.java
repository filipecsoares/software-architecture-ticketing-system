package com.fcs.orders.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "ORDERS")
@Data
public class JpaOrder {

    @Id
    @GeneratedValue
    private final Integer id;
    private Integer customerId;
    private Double totalPrice;
    private Integer cardNumber;
    private Integer cvv;
    private String cardHolderName;
    private LocalDate exp;
    private String cardBanner;
    private OrderStatus status;

    enum OrderStatus {
        RESERVED,
        PENDING,
        PAID,
        DENIED
    }
}
