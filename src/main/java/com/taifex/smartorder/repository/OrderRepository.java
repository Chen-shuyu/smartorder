package com.taifex.smartorder.repository;

import com.taifex.smartorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserId(Long userId);

    List<Order> findByUserIdAndCreatedAtBetween(Long id, LocalDateTime start, LocalDateTime end) ;
}
