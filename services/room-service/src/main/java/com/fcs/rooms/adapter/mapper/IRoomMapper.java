package com.fcs.rooms.adapter.mapper;

import com.fcs.rooms.adapter.entrypoint.dto.CreateRoomDto;
import com.fcs.rooms.entity.Room;
import com.fcs.rooms.entity.vo.Position;
import com.fcs.rooms.entity.vo.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoomMapper {
    default Room createRoomDtoToRoom(CreateRoomDto requestDto) {
        Room room = new Room();
        room.setName(requestDto.name());
        List<Seat> seats = requestDto.seats().stream()
                .map(seatDto ->
                        new Seat(seatDto.name(), new Position(seatDto.position().row(), seatDto.position().num()))
                ).toList();
        room.setSeats(seats);
        return room;
    }
}
