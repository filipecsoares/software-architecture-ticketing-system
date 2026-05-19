package com.fcs.rooms.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "allocations")
@Data
public class JpaAllocation {

    @Id
    @GeneratedValue
    private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private JpaRoom room;
}
