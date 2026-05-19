package com.fcs.rooms.adapter.dataprovider.jpa.repository;

import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPositionRepository extends JpaRepository<JpaPosition, Integer> {
    List<JpaPosition> findBySeatId(Integer seatId);
}
