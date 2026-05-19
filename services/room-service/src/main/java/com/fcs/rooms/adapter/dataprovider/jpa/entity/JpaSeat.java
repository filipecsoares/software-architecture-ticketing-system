package com.fcs.rooms.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "seats")
@Data
public class JpaSeat {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private JpaRoom room;
}
