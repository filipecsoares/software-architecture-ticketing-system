package com.fcs.rooms.adapter.dataprovider.jpa.repository;

import com.fcs.rooms.adapter.dataprovider.jpa.entity.JpaAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAllocationRepository extends JpaRepository<JpaAllocation, Integer> {
    List<JpaAllocation> findByRoomId(Integer roomId);
}
