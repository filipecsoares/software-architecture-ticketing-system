package com.fcs.admin.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateModel {

    private Integer id;
    private String name;
    private List<SeatModel> seats;
    private List<AllocationModel> allocations;
}
