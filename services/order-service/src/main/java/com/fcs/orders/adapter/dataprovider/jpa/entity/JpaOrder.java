package com.fcs.orders.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "ORDERS")
@Data
public class JpaOrder {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer customerId;
    private Double totalPrice;
    private Long cardNumber;
    private String cardHolderName;
    private LocalDate exp;
    private String cardBanner;
    private String status;
}
