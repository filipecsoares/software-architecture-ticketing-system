package com.fcs.rooms.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "positions")
@Data
public class JpaPosition {

    @Id
    @GeneratedValue
    private Integer id;
    private String roww;
    private Integer num;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private JpaSeat seat;
}
