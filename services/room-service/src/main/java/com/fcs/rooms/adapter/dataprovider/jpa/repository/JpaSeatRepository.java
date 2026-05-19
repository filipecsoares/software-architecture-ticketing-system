package com.fcs.rooms.adapter.dataprovider.jpa.repository;

import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSeatRepository extends JpaRepository<JpaSeat, Integer> {
    List<JpaSeat> findByRoomId(Integer roomId);
}
