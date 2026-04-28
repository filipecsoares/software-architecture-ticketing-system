package com.fcs.events.usecase.model;

public record RoomModel(String name, Integer totalSeats, Boolean exists, Boolean isAvailable) {
}
