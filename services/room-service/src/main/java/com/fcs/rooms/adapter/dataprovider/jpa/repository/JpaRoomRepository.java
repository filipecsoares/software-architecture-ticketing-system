package com.fcs.rooms.adapter.dataprovider.jpa.repository;

import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRoomRepository extends JpaRepository<JpaRoom, Integer> {

    Optional<JpaRoom> findByName(String name);
}
