package com.fcs.rooms.adapter.dataprovider;

import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaAllocation;
import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaPosition;
import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaRoom;
import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaSeat;
import com.fcs.rooms.adapter.dataprovider.jpa.repository.JpaAllocationRepository;
import com.fcs.rooms.adapter.dataprovider.jpa.repository.JpaPositionRepository;
import com.fcs.rooms.adapter.dataprovider.jpa.repository.JpaRoomRepository;
import com.fcs.rooms.adapter.dataprovider.jpa.repository.JpaSeatRepository;
import com.fcs.rooms.entity.Room;
import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.model.AllocationResponseModel;
import com.fcs.rooms.usecase.model.PositionResponseModel;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.model.SeatResponseModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public record JpaRoomDataProviderAdapter(JpaRoomRepository jpaRoomRepository, JpaAllocationRepository jpaAllocationRepository, JpaSeatRepository jpaSeatRepository, JpaPositionRepository jpaPositionRepository) implements RoomGateway {

    @Override
    public Integer create(Room toCreate) {
        JpaRoom jpaRoom = new JpaRoom();
        jpaRoom.setName(toCreate.getName());
        jpaRoomRepository.save(jpaRoom);

        toCreate.getSeats().forEach(seat -> {
            JpaSeat jpaSeat = new JpaSeat();
            jpaSeat.setName(seat.name());
            jpaSeat.setRoom(jpaRoom);
            jpaSeatRepository.save(jpaSeat);

            JpaPosition jpaPosition = new JpaPosition();
            jpaPosition.setRoww(seat.position().row());
            jpaPosition.setNum(seat.position().num());
            jpaPosition.setSeat(jpaSeat);
            jpaPositionRepository.save(jpaPosition);
        });

        // Nao pode ter allocation na criacao

        return jpaRoom.getId();
    }

    @Override
    public void allocate(Integer roomId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        JpaAllocation jpaAllocation = new JpaAllocation();
        jpaAllocation.setStartDateTime(startDateTime);
        jpaAllocation.setEndDateTime(endDateTime);
        JpaRoom jpaRoom = jpaRoomRepository.findById(roomId).get();
        jpaAllocation.setRoom(jpaRoom);
        jpaAllocationRepository.save(jpaAllocation);
    }

    @Override
    public boolean exists(String name) {
        Optional<JpaRoom> jpaRoomOptional = jpaRoomRepository.findByName(name);
        return jpaRoomOptional.isPresent();
    }

    @Override
    public List<RoomResponseModel> getAll() {
        return jpaRoomRepository.findAll().stream()
                .map(jpaRoom -> {
                    List<AllocationResponseModel> allocations = jpaAllocationRepository.findByRoomId(jpaRoom.getId())
                            .stream()
                            .map(jpaAllocation -> new AllocationResponseModel(jpaAllocation.getId(), jpaAllocation.getStartDateTime(), jpaAllocation.getEndDateTime()))
                            .toList();
                    List<SeatResponseModel> seats = jpaSeatRepository.findByRoomId(jpaRoom.getId())
                            .stream()
                            .map(jpaSeat -> {
                                List<PositionResponseModel> positions = jpaPositionRepository.findBySeatId(jpaSeat.getId())
                                        .stream()
                                        .map(jpaPosition -> new PositionResponseModel(jpaPosition.getId(), jpaPosition.getRoww(), jpaPosition.getNum()))
                                        .toList(); // TODO improve since it is a one-one, dont need to be list and get(0)
                                return new SeatResponseModel(jpaSeat.getId(), jpaSeat.getName(), positions.get(0));
                            })
                            .toList();
                    return new RoomResponseModel(jpaRoom.getId(), jpaRoom.getName(), seats, allocations);
                }).toList();
    }

    @Override
    public RoomResponseModel getById(Integer roomId) {
        Optional<JpaRoom> jpaRoomOptional = jpaRoomRepository.findById(roomId);
        if (jpaRoomOptional.isPresent()) {
            JpaRoom jpaRoom = jpaRoomOptional.get();
            List<AllocationResponseModel> allocations = jpaAllocationRepository.findByRoomId(jpaRoom.getId())
                    .stream()
                    .map(jpaAllocation -> new AllocationResponseModel(jpaAllocation.getId(), jpaAllocation.getStartDateTime(), jpaAllocation.getEndDateTime()))
                    .toList();
            List<SeatResponseModel> seats = jpaSeatRepository.findByRoomId(jpaRoom.getId())
                    .stream()
                    .map(jpaSeat -> {
                        List<PositionResponseModel> positions = jpaPositionRepository.findBySeatId(jpaSeat.getId())
                                .stream()
                                .map(jpaPosition -> new PositionResponseModel(jpaPosition.getId(), jpaPosition.getRoww(), jpaPosition.getNum()))
                                .toList(); // TODO improve since it is a one-one, dont need to be list and get(0)
                        return new SeatResponseModel(jpaSeat.getId(), jpaSeat.getName(), positions.get(0));
                    })
                    .toList();
            return new RoomResponseModel(jpaRoom.getId(), jpaRoom.getName(), seats, allocations);
        }
        return null;
    }

    @Override
    public void delete(Integer roomId) {
        Optional<JpaRoom> jpaRoomOptional = jpaRoomRepository.findById(roomId);
        if (jpaRoomOptional.isPresent()) {
            JpaRoom jpaRoom = jpaRoomOptional.get();
            jpaAllocationRepository.findByRoomId(jpaRoom.getId())
                    .forEach(jpaAllocationRepository::delete);
            jpaSeatRepository.findByRoomId(jpaRoom.getId())
                    .forEach(jpaSeat -> {
                        jpaPositionRepository.findBySeatId(jpaSeat.getId())
                                .forEach(jpaPositionRepository::delete);
                        jpaSeatRepository.delete(jpaSeat);
                    });
            jpaRoomRepository.delete(jpaRoom);
        }
    }
}
