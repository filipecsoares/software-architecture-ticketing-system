package com.fcs.rooms.adapter.dataprovider.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "rooms")
@Data
public class JpaRoom {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
}
