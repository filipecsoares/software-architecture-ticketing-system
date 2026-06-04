package com.fcs.customers.adapter.dataprovider.jpa.repository;

import com.fcs.customers.adapter.dataprovider.jpa.entity.JpaAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAddressRepository extends JpaRepository<JpaAddress, Integer> {
}
