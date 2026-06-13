package com.fcs.orders.adapter.dataprovider.jpa.repository;

import com.fcs.orders.adapter.dataprovider.jpa.entity.JpaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrderRepository extends JpaRepository<JpaOrder, Integer> {

    List<JpaOrder> findByCustomerId(Integer customerId);

}
